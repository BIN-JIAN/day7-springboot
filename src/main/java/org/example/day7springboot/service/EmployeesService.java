package org.example.day7springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.day7springboot.entity.Employee;
import org.example.day7springboot.exception.BigAgeAndLowSalaryException;
import org.example.day7springboot.exception.DuplicateEmployeeException;
import org.example.day7springboot.exception.EmployeeStatusException;
import org.example.day7springboot.exception.NotAmongLegalAgeException;
import org.example.day7springboot.repository.EmployeeRepository;
import org.example.day7springboot.repository.impl.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {

  @Autowired
  //private EmployeeRepository employeeRepository;
  private EmployeeRepository employeeRepository;

  public Map<String, Long> createEmployee(Employee employee) {
    validEmployeeForCreate(employee);
    //status给一个默认值;
    //employee.setStatus(true);
    employeeRepository.save(employee);
    return Map.of("id", employee.getId());
  }


  public Employee getEmployee(long id) {
    return employeeRepository.findById(id);
  }

  public List<Employee> getEmployeesByGender(String gender) {
    return employeeRepository.findAll().stream()
      .filter(e -> e.getGender().equals(gender))
      .collect(Collectors.toList());
  }

  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  //状态码放到controller
  public ResponseEntity<Void> updateEmployee(long id, Employee updatedEmployee) {
    Employee original = employeeRepository.findById(id);
    if (original == null) {
      return ResponseEntity.notFound().build();
    }
    validEmployeeForUpdate(original, updatedEmployee);
    boolean updated = employeeRepository.update(id, updatedEmployee);
    if (updated) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }


  public ResponseEntity<Void> deleteEmployee(long id) {
    Employee employee = employeeRepository.findById(id);
    if (employee == null) {
      return ResponseEntity.notFound().build();
    }
    validEmployeeForDelete(employee);
    boolean deleted = employeeRepository.delete(id);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  public Object getAllEmployees1(Integer page, Integer size) {
    List<Employee> all = employeeRepository.findAll();
    if (page == null || size == null) {
      return all;
    }
    int startIndex = (page - 1) * size;
    if (startIndex >= all.size()) {
      return new ArrayList<>();
    }
    int endIndex = Math.min(startIndex + size, all.size());
    return all.subList(startIndex, endIndex);
  }

//  public void clearEmployee() {
//    employeeRepository.clear();
//  }

  private void validEmployeeForCreate(Employee employee) {
    if (employeeRepository.findAll().stream()
      .anyMatch(e -> e.getName().equals(employee.getName()) && e.getGender()
        .equals(employee.getGender()))) {
      throw new DuplicateEmployeeException("Employee with same name and gender already exists.");
    }
    if (employee.getAge() < 18 || employee.getAge() > 65) {
      throw new NotAmongLegalAgeException("Employee age must be between 18 and 65.");
    }
    if (employee.getAge() >= 30 && employee.getSalary() < 20000) {
      throw new BigAgeAndLowSalaryException(
        "Employees over 30 must have a salary of at least 20000.");
    }
  }

  private void validEmployeeForUpdate(Employee original, Employee updatedEmployee) {
    if (!original.isStatus()) {
      throw new EmployeeStatusException("Employee status is false, cannot update.");
    }
    if (updatedEmployee.getSalary() < 20000 && updatedEmployee.getAge() > 30) {
      throw new BigAgeAndLowSalaryException(
        "Employees over 30 must have a salary of at least 20000.");
    }
  }

  private void validEmployeeForDelete(Employee employee) {
    if (!employee.isStatus()) {
      throw new EmployeeStatusException("Employee status is false, cannot delete.");
    }
  }
}
