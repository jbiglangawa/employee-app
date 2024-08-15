package com.employee.legalmatch.EmployeeSystem.mapper;

import com.employee.legalmatch.EmployeeSystem.dto.UserDTO;
import com.employee.legalmatch.EmployeeSystem.dto.UserForm;
import com.employee.legalmatch.EmployeeSystem.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserMapper userMapperInTest;

    static ZonedDateTime dateNow;

    @BeforeAll
    static void setUp() {
        dateNow = ZonedDateTime.now();
    }

    @Test
    void testMapUserToUserDTO() {
        User given = new User();
        given.setUserId(1);
        given.setUsername("user");
        given.setHash("password");
        given.setRoles("ROLE_USER");
        given.setCreatedOn(dateNow);
        given.setUpdatedOn(dateNow);

        UserDTO expected = new UserDTO("user", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        var actual = userMapperInTest.map(given);

        assertEquals(expected, actual);
    }

    @Test
    void testMapUserFormToUser() {
        UserForm given = new UserForm("user", "password", List.of("ROLE_USER"));

        User expected = new User();
        expected.setUsername("user");
        expected.setHash("password");
        expected.setRoles("ROLE_USER");

        when(passwordEncoder.encode(eq("password"))).thenReturn("password");

        var actual = userMapperInTest.map(given);

        assertEquals(expected, actual);
    }

}
