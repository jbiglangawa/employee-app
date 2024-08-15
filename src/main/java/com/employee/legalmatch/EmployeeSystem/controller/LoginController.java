package com.employee.legalmatch.EmployeeSystem.controller;

import com.employee.legalmatch.EmployeeSystem.config.ServerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {
    private final ServerConfig serverConfig;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView response = new ModelAndView("login");
        try {
            String serverAddress = serverConfig.getGraphQlUrl();
            response.addObject("serverAddress", serverAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
