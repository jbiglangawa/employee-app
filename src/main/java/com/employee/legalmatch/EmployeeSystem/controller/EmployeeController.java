package com.employee.legalmatch.EmployeeSystem.controller;

import com.employee.legalmatch.EmployeeSystem.config.ServerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final ServerConfig serverConfig;

    /**
     * Opens up employee.html and injects serverAddress via Thymeleaf
     * @return "employee" with the object serverAddress
     */
    @GetMapping
    public ModelAndView employeeCatalogue() {
        ModelAndView response = new ModelAndView("employee");
        try {
            String serverAddress = serverConfig.getGraphQlUrl();
            response.addObject("serverAddress", serverAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
