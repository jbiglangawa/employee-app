package com.employee.legalmatch.EmployeeSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "EmployeeAddress")
@Data
public class EmployeeAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Column(name = "Address1")
    private String address1;

    @Column(name = "Address2")
    private String address2;

    @Column(name = "IsPrimary")
    private Boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "EmployeeId")
    private Employee employee;
}
