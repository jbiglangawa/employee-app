package com.employee.legalmatch.EmployeeSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "EmployeeId")
    private Employee employee;
}
