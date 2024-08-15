package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.UserDTO;
import com.employee.legalmatch.EmployeeSystem.dto.UserForm;
import com.employee.legalmatch.EmployeeSystem.entity.User;
import com.employee.legalmatch.EmployeeSystem.exception.UserAlreadyExistsException;
import com.employee.legalmatch.EmployeeSystem.mapper.IUserMapper;
import com.employee.legalmatch.EmployeeSystem.repository.IUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    IUserRepository userRepository;
    @Mock
    IUserMapper userMapper;

    @InjectMocks
    UserService userServiceInTest;

    static ZonedDateTime dateNow;

    @BeforeAll
    static void setUp() {
        dateNow = ZonedDateTime.now();
    }

    @Test
    void testLoadUserByUsername() {
        String given = "user";

        User userFromDb = new User();
        userFromDb.setUserId(1);
        userFromDb.setUsername("user");
        userFromDb.setHash("password");
        userFromDb.setRoles("ROLE_USER");
        userFromDb.setCreatedOn(dateNow);
        userFromDb.setUpdatedOn(dateNow);

        UserDTO afterMap = new UserDTO("user", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        UserDTO expected = new UserDTO("user", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userFromDb));
        when(userMapper.map(any(User.class))).thenReturn(afterMap);

        UserDetails actual = userServiceInTest.loadUserByUsername(given);

        assertEquals(expected, actual);
    }

    @Test
    void testLoadNonExistingUser() {
        String given = "user1";

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userServiceInTest.loadUserByUsername(given));
    }


    @Test
    void testCreateUserAlreadyExists() {
        UserForm given = new UserForm("user", "password", List.of("ROLE_USER"));

        User existingDBUser = new User();
        existingDBUser.setUserId(1);
        existingDBUser.setUsername("user");
        existingDBUser.setHash("password");
        existingDBUser.setRoles("ROLE_USER");
        existingDBUser.setCreatedOn(dateNow);
        existingDBUser.setUpdatedOn(dateNow);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingDBUser));

        assertThrows(UserAlreadyExistsException.class, () -> userServiceInTest.createUser(given));
    }

    @Test
    void testCreateUserNewUser() {
        UserForm given = new UserForm("user", "password", List.of("ROLE_USER"));

        User mappedUserForm = new User();
        mappedUserForm.setUsername("user");
        mappedUserForm.setHash("password");
        mappedUserForm.setRoles("ROLE_USER");

        User afterSaving = new User();
        afterSaving.setUserId(1);
        afterSaving.setUsername("user");
        afterSaving.setHash("password");
        afterSaving.setRoles("ROLE_USER");
        afterSaving.setCreatedOn(dateNow);
        afterSaving.setUpdatedOn(dateNow);

        User expected = new User();
        expected.setUserId(1);
        expected.setUsername("user");
        expected.setHash("password");
        expected.setRoles("ROLE_USER");
        expected.setCreatedOn(dateNow);
        expected.setUpdatedOn(dateNow);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userMapper.map(any(UserForm.class))).thenReturn(mappedUserForm);
        when(userRepository.save(any(User.class))).thenReturn(afterSaving);

        User actual = userServiceInTest.createUser(given);

        assertEquals(expected, actual);
    }

}
