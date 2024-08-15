package com.employee.legalmatch.EmployeeSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    /**
     * Redirects to login.html after visiting home "/"
     * @return login.html
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    /**
     * Opens up login.html and injects serverAddress via Thymeleaf
     * @return "login" with the object serverAddress
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
