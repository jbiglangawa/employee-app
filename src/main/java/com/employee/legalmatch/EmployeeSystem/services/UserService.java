package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.UserForm;
import com.employee.legalmatch.EmployeeSystem.entity.User;
import com.employee.legalmatch.EmployeeSystem.exception.UserAlreadyExistsException;
import com.employee.legalmatch.EmployeeSystem.mapper.IUserMapper;
import com.employee.legalmatch.EmployeeSystem.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        return userMapper.map(user);
    }

    @Override
    public User createUser(UserForm userForm) {
        var existingUser = userRepository.findByUsername(userForm.username());
        if(existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        var user = userMapper.map(userForm);
        return userRepository.save(user);
    }
}
