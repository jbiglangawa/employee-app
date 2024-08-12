package com.employee.legalmatch.EmployeeSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "EmployeeContact")
@Data
public class EmployeeContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactId")
    private Integer contactId;

    @Column(name = "ContactInfo")
    private String contactInfo;

    @Column(name = "IsPrimary")
    private Boolean isPrimary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EmployeeId")
    private Employee employee;
}
