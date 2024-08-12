package com.employee.legalmatch.EmployeeSystem.graphql;

import com.employee.legalmatch.EmployeeSystem.dto.PageSize;
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
    public List<Employee> getEmployees(@Argument PageSize pageSize) {
        return employeeService.getEmployees(pageSize);
    }

    @SchemaMapping
    public List<EmployeeContact> contacts(Employee employee) {
        return employee.getContacts();
    }

    @SchemaMapping
    public List<EmployeeAddress> addresses(Employee employee) {
        return employee.getAddresses();
    }

    @MutationMapping
    public Integer createEmployee(@Argument Employee employee) {
        return employeeService.createEmployee(employee);
    }
}
