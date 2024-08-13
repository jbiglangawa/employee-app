package com.employee.legalmatch.EmployeeSystem.dto;

import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeDTO {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;
    private ZonedDateTime birthDate;
    private ZonedDateTime hireDate;
    private String gender;
    private String maritalStatus;
    private String currentPosition;
    private boolean clearContacts;
    private List<EmployeeContact> contacts = new ArrayList<>();
    private boolean clearAddresses;
    private List<EmployeeAddress> addresses = new ArrayList<>();

}
