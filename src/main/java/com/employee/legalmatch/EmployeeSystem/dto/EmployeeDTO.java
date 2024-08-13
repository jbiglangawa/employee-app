package com.employee.legalmatch.EmployeeSystem.dto;

import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeDTO {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;
    private LocalDateTime birthDate;
    private LocalDateTime hireDate;
    private String gender;
    private String maritalStatus;
    private String currentPosition;
    private Boolean clearContacts;
    private List<EmployeeContact> contacts = new ArrayList<>();
    private Boolean clearAddresses;
    private List<EmployeeAddress> addresses = new ArrayList<>();

}
