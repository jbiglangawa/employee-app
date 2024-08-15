package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.dto.PageSize;
import com.employee.legalmatch.EmployeeSystem.dto.PagedEmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;
import com.employee.legalmatch.EmployeeSystem.mapper.IEmployeeMapper;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeRepository;
import com.employee.legalmatch.EmployeeSystem.util.CommonUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final EntityManager entityManager;

    private final IEmployeeMapper employeeMapper;

    @Override
    public PagedEmployeeDTO getEmployees(PageSize pageSize) {
        long totalCount = employeeRepository.count();

        var results = employeeRepository.findAll(PageRequest.of(pageSize.page(), pageSize.size(), Sort.by(Sort.Direction.DESC, "createdOn", "employeeId")))
                .get()
                .toList();
        return employeeMapper.map(results, totalCount);
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer employeeId) {
        var employeeById = employeeRepository.findById(employeeId.longValue())
                .orElseThrow(() -> new EntityNotFoundException("ID " + employeeId + " could not be found"));
        return employeeMapper.map(employeeById);
    }

    @Override
    public Employee createEmployee(EmployeeDTO employee) {
        return employeeRepository.saveAndFlush(employeeMapper.map(employee));
    }

    /**
     * Granular approach for updating individual fields.
     * To delete all contacts/addresses, clearContacts/clearAddresses flag must be true
     * @param updates dto with new employee information
     * @return The updated employee
     */
    @Override
    @Transactional
    public Employee updateEmployee(EmployeeDTO updates) {
        var employee = employeeRepository.findById(Long.valueOf(updates.getEmployeeId()))
                .orElseThrow(() -> new EntityNotFoundException("ID " + updates.getEmployeeId() + " could not be found"));
        if (CommonUtil.isNotNullOrEmpty(updates.getFirstName())) {
            employee.setFirstName(updates.getFirstName());
        }
        if (CommonUtil.isNotNullOrEmpty(updates.getLastName())) {
            employee.setLastName(updates.getLastName());
        }
        if (CommonUtil.isNotNullOrEmpty(updates.getMiddleName())) {
            employee.setMiddleName(updates.getMiddleName());
        }
        if (CommonUtil.isNotNullOrEmpty(updates.getGender())) {
            employee.setGender(updates.getGender());
        }
        if (CommonUtil.isNotNullOrEmpty(updates.getMaritalStatus())) {
            employee.setMaritalStatus(updates.getMaritalStatus());
        }
        if (CommonUtil.isNotNullOrEmpty(updates.getCurrentPosition())) {
            employee.setCurrentPosition(updates.getCurrentPosition());
        }
        if (updates.getBirthDate() != null) {
            employee.setBirthDate(updates.getBirthDate());
        }
        if (updates.getHireDate() != null) {
            employee.setHireDate(updates.getHireDate());
        }

        // Handles Create/Update/Delete operations for contact changes
        if(updates.getContacts().isEmpty() && updates.isClearContacts()) {
            employee.getContacts().clear();
        }else if(!updates.getContacts().isEmpty()) {
            var newContacts = updates.getContacts().stream()
                    .filter(contact -> contact.getContactId() == null)
                    .peek(contact -> contact.setEmployee(employee))
                    .map(entityManager::merge)
                    .toList();
            var updatedContacts = new ArrayList<EmployeeContact>();
            employee.getContacts().forEach(contact -> {
                var foundContact = updates.getContacts().stream()
                        .filter(updatedContact -> Objects.equals(updatedContact.getContactId(), contact.getContactId())).findFirst();
                if (foundContact.isPresent()) {
                    contact.setContactInfo(foundContact.get().getContactInfo());
                    contact.setIsPrimary(foundContact.get().getIsPrimary());
                    updatedContacts.add(contact);
                }
            });

            updatedContacts.addAll(newContacts);
            employee.getContacts().clear();
            employee.getContacts().addAll(updatedContacts);
        }

        // Handles Create/Update/Delete operations for address changes
        if(updates.getAddresses().isEmpty() && updates.isClearAddresses()) {
            employee.getAddresses().clear();
        }else if(!updates.getAddresses().isEmpty()) {
            var newAddresses = updates.getAddresses().stream()
                    .filter(contact -> contact.getAddressId() == null)
                    .peek(contact -> contact.setEmployee(employee))
                    .map(entityManager::merge)
                    .toList();
            var updatedAddresses = new ArrayList<EmployeeAddress>();
            employee.getAddresses().forEach(contact -> {
                var foundAddress = updates.getAddresses().stream()
                        .filter(updatedAddress -> Objects.equals(updatedAddress.getAddressId(), contact.getAddressId())).findFirst();
                if (foundAddress.isPresent()) {
                    contact.setAddress1(foundAddress.get().getAddress1());
                    contact.setAddress2(foundAddress.get().getAddress2());
                    contact.setIsPrimary(foundAddress.get().getIsPrimary());
                    updatedAddresses.add(contact);
                }
            });

            updatedAddresses.addAll(newAddresses);
            employee.getAddresses().clear();
            employee.getAddresses().addAll(updatedAddresses);
        }

        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Integer deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(Long.valueOf(employeeId));
        return employeeId;
    }
}
