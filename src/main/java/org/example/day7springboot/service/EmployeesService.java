package org.example.day7springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.day7springboot.entity.Employee;
import org.example.day7springboot.repository.EmployeeRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.example.day7springboot.service.BigAgeAndLowSalaryException;

@Service
public class EmployeesService {
  @Autowired
  private EmployeeRepositry employeeRepositry;


  public Map<String, Long> createEmployee( Employee employee)  {
    if(employee.getAge() < 18 || employee.getAge() > 65) {
      throw new NotAmongLegalAgeException("Employee age must be between 18 and 65.");
    }
    else if(employee.getAge() > 30 && employee.getSalary() < 20000){
      throw new BigAgeAndLowSalaryException("Employees over 30 must have a salary of at least 20000.");
    }
    else {
      employeeRepositry.saveEmployee(employee);
      return Map.of("id", employee.getId());
    }
  }

  public Employee getEmployee( long id) {
    return employeeRepositry.findById(id);
  }


  public List<Employee> getEmployeesByGender( String gender) {
    return employeeRepositry.getEmployees().stream()
      .filter(e -> e.getGender().equals(gender))
      .collect(Collectors.toList());
  }


  public List<Employee> getAllEmployees() {
    return employeeRepositry.getEmployees();
  }



  public ResponseEntity<Void> updateEmployee( long id,  Employee updatedEmployee) {
    boolean updated = employeeRepositry.updateEmployee(id, updatedEmployee);
    if (updated) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  public ResponseEntity<Void> deleteEmployee( long id) {
    boolean deleted = employeeRepositry.deleteEmployee(id);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }


  public Object getAllEmployees1(Integer page, Integer size) {
    if (page == null || size == null) {
      return employeeRepositry.getEmployees();
    }

    int startIndex = (page - 1) * size;
    if (startIndex >= employeeRepositry.getEmployees().size()) {
      return new ArrayList<>();
    }
    int endIndex = Math.min(startIndex + size, employeeRepositry.getEmployees().size());
    return employeeRepositry.getEmployees().subList(startIndex, endIndex);
  }


  public  void clearEmployee() {
    employeeRepositry.clear();
  }

}
