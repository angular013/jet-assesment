package com.company.jetassesment.unit;

import com.company.jetassesment.model.Employee;
import com.company.jetassesment.exception.EmployeeNotFoundException;
import com.company.jetassesment.repository.EmployeeRepository;
import com.company.jetassesment.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEmployeeById_Success() throws Exception {
        UUID uuid = UUID.randomUUID();
        Employee mockEmployee = new Employee();
        // Reflection to set UUID , aa setter for it does not exist in entity
        Field field = Employee.class.getDeclaredField("uuid") ;
        field.setAccessible(true);
        field.set(mockEmployee, uuid);
        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(mockEmployee));

        Employee result = employeeService.getEmployeeById(uuid).getBody();

        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        verify(employeeRepository, times(1)).findById(uuid);
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        UUID uuid = UUID.randomUUID();
        when(employeeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(uuid));

        verify(employeeRepository, times(1)).findById(uuid);
    }

    @Test
    public void testCreateEmployee_Success() {
        Employee employeeToCreate = new Employee();
        employeeToCreate.setEmail("test@gmail.com");
        employeeToCreate.setFirstName("John");
        employeeToCreate.setLastName("Doe");
        employeeToCreate.setBirthday(LocalDate.parse("1992-08-24"));
        // Set other properties as needed

        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeToCreate);

        Employee createdEmployee = employeeService.createEmployee(employeeToCreate).getBody();

        assertNotNull(createdEmployee);
        assertEquals("John", createdEmployee.getFirstName());
        assertEquals("Doe", createdEmployee.getLastName());
        assertEquals("test@gmail.com", createdEmployee.getEmail());
        assertEquals(LocalDate.parse("1992-08-24"), createdEmployee.getBirthday());
    }


}
