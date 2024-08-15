package com.employee.legalmatch.EmployeeSystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    /**
     * Opens up employee.html and injects serverAddress via Thymeleaf
     * @return "employee" with the object serverAddress
     */
    @GetMapping
    public String employeeCatalogue() {
        return "employee";
    }
}
