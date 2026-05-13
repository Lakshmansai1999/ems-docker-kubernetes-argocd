package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = new Employee(1L, "John", "Doe", "john@example.com", "IT", "Developer", 75000.0, LocalDate.now());
    }

    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        given(employeeRepository.save(employee)).willReturn(employee);

        Employee savedEmployee = employeeService.createEmployee(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        given(employeeRepository.findAll()).willReturn(List.of(employee));

        List<Employee> employeeList = employeeService.getAllEmployees();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(1);
    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        Employee savedEmployee = employeeService.getEmployeeById(1L);

        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("doe@example.com");

        Employee updatedEmployee = employeeService.updateEmployee(1L, employee);

        assertThat(updatedEmployee.getEmail()).isEqualTo("doe@example.com");
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);
    }
}
