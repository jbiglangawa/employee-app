package com.employee.legalmatch.EmployeeSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "EmployeeAddress")
@Data
public class EmployeeAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AddressId")
    private Integer addressId;

    @Column(name = "Address1")
    private String address1;

    @Column(name = "Address2")
    private String address2;

    @Column(name = "IsPrimary")
    private Boolean isPrimary;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "EmployeeId")
    private Employee employee;
}
