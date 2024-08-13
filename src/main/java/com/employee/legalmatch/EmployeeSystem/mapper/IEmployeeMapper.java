package com.employee.legalmatch.EmployeeSystem.mapper;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;

public interface IEmployeeMapper {
    Employee map(EmployeeDTO dto);
}
