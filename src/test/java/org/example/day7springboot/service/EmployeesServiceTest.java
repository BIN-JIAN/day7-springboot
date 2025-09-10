package org.example.day7springboot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.example.day7springboot.entity.Employee;
import org.example.day7springboot.exception.BigAgeAndLowSalaryException;
import org.example.day7springboot.exception.DuplicateEmployeeException;
import org.example.day7springboot.exception.NotAmongLegalAgeException;
import org.example.day7springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EmployeesServiceTest {

  @Mock
  private EmployeeRepository employeeRepositry;
  @InjectMocks
  private EmployeesService employeesService;

  @Test
  void should_not_legal_age_when_post_then_not_create() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setAge(17);
    employee.setName("jin");
    employee.setGender("MALE");
    employee.setSalary(3000.0);
    assertThrows(NotAmongLegalAgeException.class, () -> {
      employeesService.createEmployee(employee);
    });
  }

  @Test
  void should_not_legal_age_grater_65_when_post_then_not_create() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setAge(65);
    employee.setName("jin");
    employee.setGender("MALE");
    employee.setSalary(3000.0);
    assertThrows(NotAmongLegalAgeException.class, () -> {
      employeesService.createEmployee(employee);
    });
  }

  @Test
  void should_get_employee_when_exist_then_return() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setAge(20);
    employee.setName("jin");
    employee.setGender("MALE");
    employee.setSalary(3000.0);

    when(employeesService.getEmployee(1)).thenReturn(employee);

    Employee findEmployee = employeesService.getEmployee(1);
    assertEquals(findEmployee.getId(), employee.getId());
    assertEquals(findEmployee.getAge(), employee.getAge());
    assertEquals(findEmployee.getName(), employee.getName());

    verify(employeeRepositry, times(1)).findById(1);

  }

  @Test
  void should_get_employee_when_not_exist_then_return() {

    when(employeesService.getEmployee(-1)).thenReturn(null);

    Employee findEmployee = employeesService.getEmployee(-1);
    assertNull(findEmployee);
    verify(employeeRepositry, times(1)).findById(-1);
  }

  @Test
  void should_age_exceed_30_and_salary_below_20000_when_post_then_not_create() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setAge(35);
    employee.setName("jin");
    employee.setGender("MALE");
    employee.setSalary(3000.0);
    assertThrows(BigAgeAndLowSalaryException.class, () -> {
      employeesService.createEmployee(employee);
    });
  }


  @Test
  void should_create_employee_when_age_above_30_and_salary_above_20000_then_pass() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setAge(35);
    employee.setName("Tom");
    employee.setGender("MALE");
    employee.setSalary(25000.0);

    assertDoesNotThrow(() -> {
      employeesService.createEmployee(employee);
    });
    verify(employeeRepositry, times(1)).save(employee);
  }

  @Test
  void should_not_create_employee_with_same_name_and_gender() {
    Employee existing = new Employee();
    existing.setId(1);
    existing.setName("Tom");
    existing.setGender("MALE");
    existing.setAge(25);
    existing.setSalary(30000.0);

    Employee newEmployee = new Employee();
    newEmployee.setId(2);
    newEmployee.setName("Tom");
    newEmployee.setGender("MALE");
    newEmployee.setAge(30);
    newEmployee.setSalary(25000.0);
    when(employeeRepositry.findAll()).thenReturn(java.util.List.of(existing));
    assertThrows(DuplicateEmployeeException.class, () -> {
      employeesService.createEmployee(newEmployee);
    });
  }
  @Test
  void should_create_employee_with_same_name_and_different_gender_then_pass() {
    Employee existing = new Employee();
    existing.setId(1);
    existing.setName("Tom");
    existing.setGender("MALE");
    existing.setAge(25);
    existing.setSalary(30000.0);

    Employee newEmployee = new Employee();
    newEmployee.setId(2);
    newEmployee.setName("Tom");
    newEmployee.setGender("FEMALE");
    newEmployee.setAge(30);
    newEmployee.setSalary(25000.0);
    when(employeeRepositry.findAll()).thenReturn(java.util.List.of(existing));
    assertDoesNotThrow( () -> {
      employeesService.createEmployee(newEmployee);
    });
  }
}