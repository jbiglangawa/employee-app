package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.repository.EmployeeAddressRepository;
import com.employee.legalmatch.EmployeeSystem.repository.EmployeeContactRepository;
import com.employee.legalmatch.EmployeeSystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeContactRepository employeeContactRepository;
    private final EmployeeAddressRepository employeeAddressRepository;
}
