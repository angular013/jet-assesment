package com.company.jetassesment.integration;

import com.company.jetassesment.model.Employee;
import com.company.jetassesment.repository.EmployeeRepository;
import com.company.jetassesment.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testGetEmployeeById() {
        // Arrange
        Employee employeeToCreate = new Employee();
        employeeToCreate.setFirstName("John");
        employeeToCreate.setLastName("Doe");
        employeeToCreate.setEmail("john.doe@example.com");
        employeeToCreate.setBirthday(LocalDate.parse("2007-08-23"));
        Employee savedEmployee = employeeRepository.save(employeeToCreate);

        // Act
        Employee retrievedEmployee = employeeService.getEmployeeById(savedEmployee.getUuid()).getBody();

        // Assert
        assertEquals(savedEmployee.getUuid(), retrievedEmployee.getUuid());
        assertEquals(savedEmployee.getFirstName(), retrievedEmployee.getFirstName());
        assertEquals(savedEmployee.getLastName(), retrievedEmployee.getLastName());
        assertEquals(savedEmployee.getEmail(), retrievedEmployee.getEmail());
        assertEquals(savedEmployee.getBirthday(), retrievedEmployee.getBirthday());
    }

    @AfterEach
    public void cleanUp() {
        employeeRepository.deleteAll(); // Delete all data after each test
    }

}
