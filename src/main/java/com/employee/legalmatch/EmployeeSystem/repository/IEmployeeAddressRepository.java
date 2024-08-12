package com.employee.legalmatch.EmployeeSystem.repository;

import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeAddressRepository extends JpaRepository<EmployeeAddress, Long> {

}
