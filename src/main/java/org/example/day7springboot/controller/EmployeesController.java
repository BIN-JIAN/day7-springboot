package org.example.day7springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.day7springboot.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeesController {

  private List<Employee> employees = new ArrayList<>();

  @PostMapping("/employees")
  public Map<String, Long> createEmployee(@RequestBody Employee employee) {
    employee.setId(employees.size() + 1);
    employees.add(employee);
    return Map.of("id", employee.getId());
  }
  @GetMapping("/employees/{id}")
  public Employee getEmployee(@PathVariable long id) {
   return employees.stream()
     .filter(employee-> employee.getId()==id)
     .findFirst()
     .orElse(null);
  }

  @GetMapping("/employees/gender")
  public List<Employee> getEmployeesByGender(@RequestParam String gender) {
    return employees.stream()
      .filter(e -> e.getGender().equals(gender))
      .collect(Collectors.toList());
  }

  @GetMapping("/employees")
  public List<Employee> getAllEmployees() {
    return employees;
  }

  @PutMapping("/employees/{id}")
  public ResponseEntity<Void> updateEmployee(@PathVariable long id, @RequestBody Employee updatedEmployee) {
    for (int i = 0; i < employees.size(); i++) {
      if (employees.get(i).getId() == id) {
        updatedEmployee.setId(id);
        employees.set(i, updatedEmployee);
        return ResponseEntity.noContent().build();
      }
    }
    return ResponseEntity.notFound().build();
  }
}
