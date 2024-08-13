package com.employee.legalmatch.EmployeeSystem.mapper;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper implements IEmployeeMapper {
    @Override
    public Employee map(EmployeeDTO dto) {
        var newEmployee = new Employee();
        newEmployee.setEmployeeId(dto.getEmployeeId());
        newEmployee.setFirstName(dto.getFirstName());
        newEmployee.setLastName(dto.getLastName());
        newEmployee.setMiddleName(dto.getMiddleName());
        newEmployee.setBirthDate(dto.getBirthDate());
        newEmployee.setHireDate(dto.getHireDate());
        newEmployee.setMaritalStatus(dto.getMaritalStatus());
        newEmployee.setCurrentPosition(dto.getCurrentPosition());
        newEmployee.setGender(dto.getGender());
        newEmployee.setContacts(dto.getContacts().stream().peek(e -> e.setEmployee(newEmployee)).toList());
        newEmployee.setAddresses(dto.getAddresses().stream().peek(e -> e.setEmployee(newEmployee)).toList());
        return newEmployee;
    }

}
