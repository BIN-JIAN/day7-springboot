package org.example.day7springboot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.day7springboot.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
    for(int i = 0; i< employees.size();i++){
      if(employees.get(i).getId() == id){
        employees.remove(i);
        return ResponseEntity.noContent().build();
      }
    }
    return ResponseEntity.notFound().build();
  }


  @GetMapping("/employees1")
  public Object getAllEmployees1(@RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
    if (page == null || size == null) {
      return employees;
    }

    int startIndex = (page - 1) * size;
    if (startIndex >= employees.size()) {
      return new ArrayList<>();
    }
    int endIndex = Math.min(startIndex + size, employees.size());
    return employees.subList(startIndex, endIndex);
  }

}
