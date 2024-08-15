package com.employee.legalmatch.EmployeeSystem.graphql;

import com.employee.legalmatch.EmployeeSystem.dto.*;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;
import com.employee.legalmatch.EmployeeSystem.mapper.IUserMapper;
import com.employee.legalmatch.EmployeeSystem.services.IEmployeeService;
import com.employee.legalmatch.EmployeeSystem.services.IJwtService;
import com.employee.legalmatch.EmployeeSystem.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQlController {
    private final IEmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;

    private final IUserService userService;
    private final IUserMapper userMapper;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @QueryMapping
    public PagedEmployeeDTO getEmployees(@Argument PageSize pageSize) {
        return employeeService.getEmployees(pageSize);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @QueryMapping
    public EmployeeDTO getEmployeeById(@Argument Integer employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @SchemaMapping
    public List<EmployeeContact> contacts(EmployeeDTO employee) {
        return employee.getContacts();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @SchemaMapping
    public List<EmployeeAddress> addresses(EmployeeDTO employee) {
        return employee.getAddresses();
    }

    @Secured("ROLE_ADMIN")
    @MutationMapping
    public Integer createEmployee(@Argument EmployeeDTO employee) {
        return employeeService.createEmployee(employee).getEmployeeId();
    }

    @Secured("ROLE_ADMIN")
    @MutationMapping
    public Integer updateEmployee(@Argument EmployeeDTO employee) {
        return employeeService.updateEmployee(employee).getEmployeeId();
    }

    @Secured("ROLE_ADMIN")
    @MutationMapping
    public Integer deleteEmployee(@Argument Integer employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }

    @MutationMapping
    public AuthToken getToken(@Argument LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginForm.username());
            UserDetails userDetails = userService.loadUserByUsername(loginForm.username());
            return new AuthToken(token, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        } else {
            throw new UsernameNotFoundException("Invalid request");
        }
    }

    @MutationMapping
    public Integer createUser(@Argument UserForm userForm) {
        var createdUser = userService.createUser(userForm);
        return createdUser.getUserId();
    }
}
