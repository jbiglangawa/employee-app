package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("userDetailsService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("user")) {
            UserDTO userDetails = new UserDTO();
            userDetails.setUsername("user");
            userDetails.setPassword(passwordEncoder.encode("password"));
            userDetails.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));

            return userDetails;
        }else if(username.equals("admin")) {
            UserDTO userDetails = new UserDTO();
            userDetails.setUsername("admin");
            userDetails.setPassword(passwordEncoder.encode("password"));
            userDetails.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")));

            return userDetails;
        }
        return null;
    }
}
