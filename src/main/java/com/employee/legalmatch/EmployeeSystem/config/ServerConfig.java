package com.employee.legalmatch.EmployeeSystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ServerConfig {
    private final Environment environment;

    public Integer getServerPort() {
        return Integer.valueOf(Objects.requireNonNull(environment.getProperty("local.server.port")));
    }

    public String getServerAddress() {
        return InetAddress.getLoopbackAddress().getHostAddress();
    }

    public String getGraphQlUrl() throws UnknownHostException {
        return String.format("http://%s:%d", getServerAddress(), getServerPort());
    }
}
