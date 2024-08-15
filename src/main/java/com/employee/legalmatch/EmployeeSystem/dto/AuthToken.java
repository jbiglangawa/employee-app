package com.employee.legalmatch.EmployeeSystem.dto;

import java.util.List;

public record AuthToken(String token, List<String> roles) {
}
