package com.employee.legalmatch.EmployeeSystem.mapper;

import com.employee.legalmatch.EmployeeSystem.dto.UserDTO;
import com.employee.legalmatch.EmployeeSystem.dto.UserForm;
import com.employee.legalmatch.EmployeeSystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserMapper implements IUserMapper {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO map(User user) {
        var authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).toList();
        return new UserDTO(user.getUsername(), user.getHash(), authorities);
    }

    @Override
    public User map(UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.username());
        user.setHash(passwordEncoder.encode(userForm.password()));
        user.setRoles(String.join(",", userForm.roles()));
        return user;
    }
}
