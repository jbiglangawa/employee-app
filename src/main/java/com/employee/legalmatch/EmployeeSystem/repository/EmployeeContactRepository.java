package com.employee.legalmatch.EmployeeSystem.repository;

import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeContactRepository extends JpaRepository<EmployeeContact, Long> {

}
