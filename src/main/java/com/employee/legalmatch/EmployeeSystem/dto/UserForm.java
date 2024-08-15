package com.employee.legalmatch.EmployeeSystem.dto;

import java.util.List;

public record UserForm(String username, String password, List<String> roles) {

}
