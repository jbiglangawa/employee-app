package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeAddressRepository;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeContactRepository;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    IEmployeeRepository employeeRepository;
    @Mock
    IEmployeeContactRepository employeeContactRepository;
    @Mock
    IEmployeeAddressRepository employeeAddressRepository;

    @InjectMocks
    EmployeeService employeeService;

}
