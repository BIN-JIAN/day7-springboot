package org.example.day7springboot.repository;

import java.util.List;
import org.example.day7springboot.entity.Employee;

public interface EmployeeRepository {
  void save(Employee employee);

  Employee findById(long id);

  List<Employee> findAll();

  boolean update(long id, Employee employee);

  boolean delete(long id);

}
