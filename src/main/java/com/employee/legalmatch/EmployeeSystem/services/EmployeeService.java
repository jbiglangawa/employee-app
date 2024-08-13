package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.PageSize;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeAddressRepository;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeContactRepository;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final IEmployeeContactRepository employeeContactRepository;
    private final IEmployeeAddressRepository employeeAddressRepository;
    private final EntityManager entityManager;

    @Override
    public List<Employee> getEmployees(PageSize pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageSize.page(), pageSize.size()))
                .get()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Integer createEmployee(Employee employee) {
        var savedEmployee = employeeRepository.save(employee);
        if(!employee.getContacts().isEmpty()) {
            employeeContactRepository.saveAll(employee.getContacts().stream()
                    .peek(ec -> ec.setEmployee(employee))
                    .collect(Collectors.toList()));
        }
        if(!employee.getAddresses().isEmpty()) {
            employeeAddressRepository.saveAll(employee.getAddresses().stream()
                    .peek(ec -> ec.setEmployee(employee))
                    .collect(Collectors.toList()));
        }
        return savedEmployee.getEmployeeId();
    }

    @Override
    @Transactional
    public Integer updateEmployee(Employee updates) {
        Optional<Employee> byId = employeeRepository.findById(Long.valueOf(updates.getEmployeeId()));
        if(byId.isEmpty()) {
            throw new EntityNotFoundException("ID " + updates.getEmployeeId() + " could not be found");
        }
        var employee = byId.get();
        if (!employee.getFirstName().equals(updates.getFirstName())) {
            employee.setFirstName(updates.getFirstName());
        }
        if (!employee.getLastName().equals(updates.getLastName())) {
            employee.setLastName(updates.getLastName());
        }
        if (!employee.getMiddleName().equals(updates.getMiddleName())) {
            employee.setMiddleName(updates.getMiddleName());
        }
        if (employee.getAge() == null || !employee.getAge().equals(updates.getAge())) {
            employee.setAge(updates.getAge());
        }
        if (employee.getBirthDate() == null || !employee.getBirthDate().equals(updates.getBirthDate())) {
            employee.setBirthDate(updates.getBirthDate());
        }
        if (employee.getHireDate() == null || !employee.getHireDate().equals(updates.getHireDate())) {
            employee.setHireDate(updates.getHireDate());
        }
        if (!employee.getGender().equals(updates.getGender())) {
            employee.setGender(updates.getGender());
        }
        if (!employee.getMaritalStatus().equals(updates.getMaritalStatus())) {
            employee.setMaritalStatus(updates.getMaritalStatus());
        }
        if (!employee.getCurrentPosition().equals(updates.getCurrentPosition())) {
            employee.setCurrentPosition(updates.getCurrentPosition());
        }

        // Handles Create/Update/Delete operations for contact changes
        if(!employee.getContacts().isEmpty() || !updates.getContacts().isEmpty()) {
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
        if(!employee.getAddresses().isEmpty() || !updates.getAddresses().isEmpty()) {
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

        employeeRepository.save(employee);
        return employee.getEmployeeId();
    }

    @Override
    public Integer deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(Long.valueOf(employeeId));
        return employeeId;
    }
}
