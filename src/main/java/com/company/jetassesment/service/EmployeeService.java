package com.company.jetassesment.service;

import com.company.jetassesment.exception.CustomValidationException;
import com.company.jetassesment.exception.DuplicateEmailException;
import com.company.jetassesment.exception.EmployeeNotFoundException;
import com.company.jetassesment.kafka.EmployeeEvent;
import com.company.jetassesment.model.Employee;
import com.company.jetassesment.repository.EmployeeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private KafkaTemplate<String, EmployeeEvent> kafkaTemplate;

    private void employeeRequestValidator(Employee employee) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (!violations.isEmpty()) { throw new CustomValidationException("Employee request parameters validation failed");}
    }

    private boolean isEmailUnique(String email) {
        Employee existingEmployee = employeeRepository.findByEmail(email);
        return existingEmployee == null; // Return true if email is unique, false if not
    }

    public ResponseEntity<Employee> createEmployee(Employee employee) {
        employeeRequestValidator(employee);
        if (!isEmailUnique(employee.getEmail())) {
            throw new DuplicateEmailException(employee.getEmail());
        }

        Employee savedEmployee = employeeRepository.save(employee);
        // Publish Kafka event
        EmployeeEvent event = new EmployeeEvent("CREATE", employee);
        kafkaTemplate.send("employee-events", event);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    public ResponseEntity<Employee> getEmployeeById(UUID uuid) {
        Employee employee = employeeRepository.findById(uuid)
                .orElseThrow(() -> new EmployeeNotFoundException(uuid));
        return ResponseEntity.ok(employee);
    }

    public Employee updateEmployee(UUID uuid, Employee employee) {
        employeeRequestValidator(employee);
        Employee existingEmployee = employeeRepository.findById(uuid)
                .orElseThrow(() -> new EmployeeNotFoundException(uuid));

        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setBirthday(employee.getBirthday());
        existingEmployee.setHobbies(employee.getHobbies());

        // Publish Kafka event
        EmployeeEvent event = new EmployeeEvent("UPDATE", employee);
        kafkaTemplate.send("employee-events", event);

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(UUID uuid) {
        Employee employee = employeeRepository.findById(uuid)
                .orElseThrow(() -> new EmployeeNotFoundException(uuid));
        employeeRepository.deleteById(uuid);
        // Publish Kafka event
        EmployeeEvent event = new EmployeeEvent("DELETE", employee);
        kafkaTemplate.send("employee-events", event);
    }
}

