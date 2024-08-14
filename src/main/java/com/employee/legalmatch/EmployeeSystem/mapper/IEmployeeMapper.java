package com.employee.legalmatch.EmployeeSystem.mapper;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.dto.PagedEmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;

import java.util.List;

public interface IEmployeeMapper {
    Employee map(EmployeeDTO dto);
    EmployeeDTO map(Employee employee);
    PagedEmployeeDTO map(List<Employee> dto, Long totalCount);
}
