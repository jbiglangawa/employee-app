package com.employee.legalmatch.EmployeeSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZonedDateTime;

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

    @Column(name = "CreatedOn")
    private ZonedDateTime createdOn;

    @Column(name = "UpdatedOn")
    private ZonedDateTime updatedOn;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "EmployeeId")
    private Employee employee;
}
