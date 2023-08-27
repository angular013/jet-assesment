package com.company.jetassesment.repository;

import com.company.jetassesment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Employee findByEmail(String email);
}