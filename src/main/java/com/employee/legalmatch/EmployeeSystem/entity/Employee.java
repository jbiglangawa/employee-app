package com.employee.legalmatch.EmployeeSystem.entity;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeId")
    private Integer employeeId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "MiddleName")
    private String middleName;

    @Column(name = "Age")
    private Integer age;

    @Column(name = "BirthDate")
    private LocalDateTime birthDate;

    @Column(name = "HireDate")
    private LocalDateTime hireDate;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "MaritalStatus")
    private String maritalStatus;

    @Column(name = "CurrentPosition")
    private String currentPosition;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EmployeeContact> contacts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EmployeeAddress> addresses = new ArrayList<>();

    public static Employee fromDTO(EmployeeDTO dto) {
        var newEmployee = new Employee();
        newEmployee.setEmployeeId(dto.getEmployeeId());
        newEmployee.setFirstName(dto.getFirstName());
        newEmployee.setLastName(dto.getLastName());
        newEmployee.setMiddleName(dto.getMiddleName());
        newEmployee.setAge(dto.getAge());
        newEmployee.setBirthDate(dto.getBirthDate());
        newEmployee.setHireDate(dto.getHireDate());
        newEmployee.setMaritalStatus(dto.getMaritalStatus());
        newEmployee.setCurrentPosition(dto.getCurrentPosition());
        newEmployee.setContacts(dto.getContacts().stream().peek(e -> e.setEmployee(newEmployee)).toList());
        newEmployee.setAddresses(dto.getAddresses().stream().peek(e -> e.setEmployee(newEmployee)).toList());
        return newEmployee;
    }

}
