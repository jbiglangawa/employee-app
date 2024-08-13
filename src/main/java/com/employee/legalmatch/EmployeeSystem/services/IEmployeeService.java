package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.dto.PageSize;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getEmployees(PageSize pageSize);

    Employee createEmployee(EmployeeDTO employee);

    Employee updateEmployee(EmployeeDTO employee);

    Integer deleteEmployee(Integer employeeId);
}
