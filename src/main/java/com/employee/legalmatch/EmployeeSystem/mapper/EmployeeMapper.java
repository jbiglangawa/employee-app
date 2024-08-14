package com.employee.legalmatch.EmployeeSystem.mapper;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.dto.PagedEmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public EmployeeDTO map(Employee employee) {
        var dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setMiddleName(employee.getMiddleName());
        dto.setBirthDate(employee.getBirthDate());
        dto.setHireDate(employee.getHireDate());
        dto.setMaritalStatus(employee.getMaritalStatus());
        dto.setCurrentPosition(employee.getCurrentPosition());
        dto.setGender(employee.getGender());
        dto.setContacts(employee.getContacts());
        dto.setAddresses(employee.getAddresses());
        return dto;
    }

    @Override
    public PagedEmployeeDTO map(List<Employee> dto, Long totalCount) {
        var pagedEmployeeDTO = new PagedEmployeeDTO();
        pagedEmployeeDTO.setEmployees(dto.stream().map(this::map).toList());
        pagedEmployeeDTO.setTotalCount(totalCount);
        return pagedEmployeeDTO;
    }
}
