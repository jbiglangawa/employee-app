package com.employee.legalmatch.EmployeeSystem.mapper;

import com.employee.legalmatch.EmployeeSystem.dto.UserDTO;
import com.employee.legalmatch.EmployeeSystem.dto.UserForm;
import com.employee.legalmatch.EmployeeSystem.entity.User;

public interface IUserMapper {
    UserDTO map(User user);

    User map(UserForm userForm);
}
