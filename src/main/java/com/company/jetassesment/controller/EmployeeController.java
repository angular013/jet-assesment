package com.company.jetassesment.controller;

import com.company.jetassesment.model.Employee;
import com.company.jetassesment.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Employee", description = "Employee management APIs")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID uuid) {
        return employeeService.getEmployeeById(uuid);
    }

    @PutMapping("/{uuid}")
    public Employee updateEmployee(@PathVariable UUID uuid, @RequestBody Employee employee) {
        return employeeService.updateEmployee(uuid, employee);
    }

    @DeleteMapping("/{uuid}")
    public void deleteEmployee(@PathVariable UUID uuid) {
        employeeService.deleteEmployee(uuid);
    }
}