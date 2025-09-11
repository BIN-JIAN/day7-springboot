package org.example.day7springboot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.example.day7springboot.dto.RequestDto;
import org.example.day7springboot.entity.Employee;
import org.example.day7springboot.exception.BigAgeAndLowSalaryException;
import org.example.day7springboot.exception.DuplicateEmployeeException;
import org.example.day7springboot.exception.EmployeeStatusException;
import org.example.day7springboot.exception.NotAmongLegalAgeException;
import org.example.day7springboot.repository.impl.EmployeeRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EmployeesServiceTest {

  @Mock
  private EmployeeRepositoryImpl employeeRepositry;
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
    employee.setAge(66);
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

  @Test
  void should_delete_employee_when_status_true_then_success() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setName("Tom");
    employee.setGender("MALE");
    employee.setAge(30);
    employee.setSalary(25000.0);
    employee.setStatus(true);

    when(employeeRepositry.delete(1)).thenReturn(true);
    doReturn(employee).when(employeeRepositry).findById(anyLong());
    assertDoesNotThrow(() -> {
      employeesService.deleteEmployee(1);
    });
    verify(employeeRepositry, times(1)).delete(1);
  }

  @Test
  void should_not_delete_employee_when_status_false_then_throw_exception() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setName("Tom");
    employee.setGender("MALE");
    employee.setAge(30);
    employee.setSalary(25000.0);
    employee.setStatus(false);

    when(employeeRepositry.findById(1)).thenReturn(employee);
    assertThrows(EmployeeStatusException.class, () -> {
      employeesService.deleteEmployee(1);
    });
    verify(employeeRepositry, times(0)).delete(1);
  }

  @Test
  void should_update_employee_when_status_true_and_salary_above_20000_then_success() {
    Employee original = new Employee();
    original.setId(1);
    original.setName("Tom");
    original.setGender("MALE");
    original.setAge(35);
    original.setSalary(18000.0);
    original.setStatus(true);

    RequestDto updatedDto = new org.example.day7springboot.dto.RequestDto();
    updatedDto.setName("Tom");
    updatedDto.setGender("MALE");
    updatedDto.setAge(35);
    updatedDto.setSalary(25000.0);

    when(employeeRepositry.findById(1)).thenReturn(original);
    when(employeeRepositry.update(1, updatedDto)).thenReturn(true);

    assertDoesNotThrow(() -> {
      employeesService.updateEmployee(1, updatedDto);
    });
    verify(employeeRepositry, times(1)).update(1, updatedDto);
  }

  @Test
  void should_not_update_employee_when_status_true_and_salary_below_20000_and_age_above_30_then_throw_exception() {
    Employee original = new Employee();
    original.setId(1);
    original.setName("Tom");
    original.setGender("MALE");
    original.setAge(35);
    original.setSalary(18000.0);
    original.setStatus(true);
    RequestDto updatedDto = new org.example.day7springboot.dto.RequestDto();
    updatedDto.setName("Tom");
    updatedDto.setGender("MALE");
    updatedDto.setAge(35);
    updatedDto.setSalary(15000.0);

    when(employeeRepositry.findById(1)).thenReturn(original);

    assertThrows(BigAgeAndLowSalaryException.class, () -> employeesService.updateEmployee(1, updatedDto));
    verify(employeeRepositry, times(0)).update(1, updatedDto);
  }

  @Test
  void should_not_update_employee_when_status_false_then_throw_exception() {
    Employee original = new Employee();
    original.setId(1);
    original.setName("Tom");
    original.setGender("MALE");
    original.setAge(35);
    original.setSalary(18000.0);
    original.setStatus(false);
    RequestDto updatedDto = new org.example.day7springboot.dto.RequestDto();
    updatedDto.setName("Tom");
    updatedDto.setGender("MALE");
    updatedDto.setAge(35);
    updatedDto.setSalary(25000.0);

    when(employeeRepositry.findById(1)).thenReturn(original);

    assertThrows(EmployeeStatusException.class, () -> employeesService.updateEmployee(1, updatedDto));
    verify(employeeRepositry, times(0)).update(1, updatedDto);
  }
}