package com.employee.legalmatch.EmployeeSystem.repository;

import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
}
