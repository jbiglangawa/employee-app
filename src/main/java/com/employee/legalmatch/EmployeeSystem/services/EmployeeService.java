package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.PageSize;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeAddressRepository;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeContactRepository;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final IEmployeeContactRepository employeeContactRepository;
    private final IEmployeeAddressRepository employeeAddressRepository;

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
}
