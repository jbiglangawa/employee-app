package com.employee.legalmatch.EmployeeSystem.controller;

import com.employee.legalmatch.EmployeeSystem.config.ServerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;

@RequestMapping("/")
@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final ServerConfig serverConfig;

    @GetMapping("/")
    public ModelAndView employeeCatalogue() {
        ModelAndView response = new ModelAndView("index");
        try {
            String serverAddress = serverConfig.getGraphQlUrl();
            response.addObject("serverAddress", serverAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}
