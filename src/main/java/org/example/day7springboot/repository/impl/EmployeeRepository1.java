package org.example.day7springboot.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.example.day7springboot.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository1 {

  private final List<Employee> employees = new ArrayList<>();

  public void save(Employee employee) {
    employee.setId(employees.size() + 1);
    employees.add(employee);
  }


  public Employee findById(long id) {
    return employees.stream()
      .filter(e -> e.getId() == id)
      .findFirst()
      .orElse(null);
  }

  public List<Employee> findAll() {
    return employees;
  }

  public boolean update(long id, Employee employee) {
    for (int i = 0; i < employees.size(); i++) {
      if (employees.get(i).getId() == id) {
        //employee.setId(id);
        employees.set(i, employee);
        return true;
      }
    }
    return false;
  }

  public boolean delete(long id) {
    for (int i = 0; i < employees.size(); i++) {
      if (employees.get(i).getId() == id) {
        employees.remove(i);
        return true;
      }
    }
    return false;
  }

  //status置false,将处理逻辑放到service

  public void clear() {
    employees.clear();
  }
}
