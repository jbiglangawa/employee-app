package com.employee.legalmatch.EmployeeSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
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

    @Column(name = "BirthDate")
    private ZonedDateTime birthDate;

    @Column(name = "HireDate")
    private ZonedDateTime hireDate;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "MaritalStatus")
    private String maritalStatus;

    @Column(name = "CurrentPosition")
    private String currentPosition;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedOn")
    private ZonedDateTime createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedOn")
    private ZonedDateTime updatedOn;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EmployeeContact> contacts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EmployeeAddress> addresses = new ArrayList<>();
}
