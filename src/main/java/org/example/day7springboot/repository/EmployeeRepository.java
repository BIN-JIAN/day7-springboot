package org.example.day7springboot.repository;

import java.util.List;
import org.example.day7springboot.dto.RequestDto;
import org.example.day7springboot.entity.Employee;

public interface EmployeeRepository {
  void save(Employee employee);

  Employee findById(long id);

  List<Employee> findAll();

  boolean update(long id, RequestDto requestDto);

  boolean delete(long id);

}
