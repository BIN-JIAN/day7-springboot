package org.example.day7springboot.controller;

import java.util.List;
import java.util.Map;
import org.example.day7springboot.entity.Employee;
import org.example.day7springboot.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private EmployeesService employeesService;

  @PostMapping("/employees")
  public Map<String, Long> createEmployee(@RequestBody Employee employee) {
    return employeesService.createEmployee(employee);
  }

  @GetMapping("/employees/{id}")
  public Employee getEmployee(@PathVariable long id) {
    return employeesService.getEmployee(id);
  }

  @GetMapping("/employees/gender")
  public List<Employee> getEmployeesByGender(@RequestParam String gender) {
    return employeesService.getEmployeesByGender(gender);
  }

  @GetMapping("/employees")
  public List<Employee> getAllEmployees() {
    return employeesService.getAllEmployees();
  }


  @PutMapping("/employees/{id}")
  public ResponseEntity<Void> updateEmployee(@PathVariable long id,
    @RequestBody Employee updatedEmployee) {
    return employeesService.updateEmployee(id, updatedEmployee);
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
    return employeesService.deleteEmployee(id);
  }


  @GetMapping("/employees1")
  public Object getAllEmployees1(@RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
    return employeesService.getAllEmployees1(page, size);
  }

//  public void clearEmployee() {
//    employeesService.clearEmployee();
//  }
}
