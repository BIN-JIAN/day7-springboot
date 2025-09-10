package org.example.day7springboot.repository;

import java.util.ArrayList;
import java.util.List;
import org.example.day7springboot.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositry {
  private List<Employee> employees = new ArrayList<>();

  public void saveEmployee(Employee employee) {
    employee.setId(employees.size() + 1);
    employees.add(employee);
  }

  public Employee findById(long id) {
    return employees.stream()
      .filter(e -> e.getId() == id)
      .findFirst()
      .orElse(null);
  }

  public List<Employee> getEmployeesByGender(String gender) {
    return employees.stream()
      .filter(e -> e.getGender().equals(gender))
      .toList();
  }


  public boolean updateEmployee(long id, Employee employee) {
    for (int i = 0; i < employees.size(); i++) {
      if (employees.get(i).getId() == id) {
        employee.setId(id);
        employees.set(i, employee);
        return true;
      }
    }
    return false;
  }

  public boolean deleteEmployee(long id) {
    for (int i = 0; i < employees.size(); i++) {
      if (employees.get(i).getId() == id) {
        employees.remove(i);
        return true;
      }
    }
    return false;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void clear() {
    employees.clear();
  }

}
