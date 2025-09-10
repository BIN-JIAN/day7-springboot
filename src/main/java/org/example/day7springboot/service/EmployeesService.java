package org.example.day7springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.day7springboot.entity.Employee;
import org.example.day7springboot.exception.NotAmongLegalAgeException;
import org.example.day7springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.example.day7springboot.exception.BigAgeAndLowSalaryException;
import org.example.day7springboot.exception.DuplicateEmployeeException;

@Service
public class EmployeesService {
  @Autowired
  private EmployeeRepository employeeRepository;

  public Map<String, Long> createEmployee(Employee employee) {
    boolean exists = employeeRepository.findAll().stream()
      .anyMatch(e -> e.getName().equals(employee.getName()) && e.getGender().equals(employee.getGender()));
    if (exists) {
      throw new DuplicateEmployeeException("Employee with same name and gender already exists.");
    }
    if (employee.getAge() < 18 || employee.getAge() > 65) {
      throw new NotAmongLegalAgeException("Employee age must be between 18 and 65.");
    } else if (employee.getAge() > 30 && employee.getSalary() < 20000) {
      throw new BigAgeAndLowSalaryException("Employees over 30 must have a salary of at least 20000.");
    } else {
      employeeRepository.save(employee);
      return Map.of("id", employee.getId());
    }
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

  public ResponseEntity<Void> updateEmployee(long id, Employee updatedEmployee) {
    boolean updated = employeeRepository.update(id, updatedEmployee);
    if (updated) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  public ResponseEntity<Void> deleteEmployee(long id) {
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

  public void clearEmployee() {
    employeeRepository.clear();
  }
}
