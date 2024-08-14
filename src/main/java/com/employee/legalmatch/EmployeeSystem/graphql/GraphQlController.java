package com.employee.legalmatch.EmployeeSystem.graphql;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.dto.PageSize;
import com.employee.legalmatch.EmployeeSystem.dto.PagedEmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;
import com.employee.legalmatch.EmployeeSystem.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQlController {
    private final IEmployeeService employeeService;

    @QueryMapping
    public PagedEmployeeDTO getEmployees(@Argument PageSize pageSize) {
        return employeeService.getEmployees(pageSize);
    }

    @QueryMapping
    public EmployeeDTO getEmployeeById(@Argument Integer employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @SchemaMapping
    public List<EmployeeContact> contacts(EmployeeDTO employee) {
        return employee.getContacts();
    }

    @SchemaMapping
    public List<EmployeeAddress> addresses(EmployeeDTO employee) {
        return employee.getAddresses();
    }

    @MutationMapping
    public Integer createEmployee(@Argument EmployeeDTO employee) {
        return employeeService.createEmployee(employee).getEmployeeId();
    }

    @MutationMapping
    public Integer updateEmployee(@Argument EmployeeDTO employee) {
        return employeeService.updateEmployee(employee).getEmployeeId();
    }

    @MutationMapping
    public Integer deleteEmployee(@Argument Integer employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
