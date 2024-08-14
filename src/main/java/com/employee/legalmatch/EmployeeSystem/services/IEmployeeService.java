package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.dto.PageSize;
import com.employee.legalmatch.EmployeeSystem.dto.PagedEmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;

public interface IEmployeeService {
    PagedEmployeeDTO getEmployees(PageSize pageSize);

    EmployeeDTO getEmployeeById(Integer employeeId);

    Employee createEmployee(EmployeeDTO employee);

    Employee updateEmployee(EmployeeDTO employee);

    Integer deleteEmployee(Integer employeeId);
}
