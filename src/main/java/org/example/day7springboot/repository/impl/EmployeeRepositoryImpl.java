package org.example.day7springboot.repository.impl;

import java.util.List;
import java.util.Optional;
import org.example.day7springboot.dto.RequestDto;
import org.example.day7springboot.entity.Employee;
import org.example.day7springboot.repository.EmployeeRepository;
import org.example.day7springboot.repository.dao.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

  @Autowired
  private EmployeeJpaRepository employeeJpaRepository;

  @Override
  public void save(Employee employee) {
   employeeJpaRepository.save(employee);
  }

  @Override
  public Employee findById(long id) {
    return employeeJpaRepository.findById(id).orElse(null);
  }
  @Override
  public List<Employee> findAll() {

    return employeeJpaRepository.findAll();
  }

  @Override
  public boolean update(long id, RequestDto requestDto) {
    Optional<Employee> optionalEmployee = employeeJpaRepository.findById(id);

    if (optionalEmployee.isPresent()) {
      Employee existingEmployee = optionalEmployee.get();
      existingEmployee.setName(requestDto.getName());
      existingEmployee.setAge(requestDto.getAge());
      existingEmployee.setSalary(requestDto.getSalary());
      existingEmployee.setGender(requestDto.getGender());
      employeeJpaRepository.save(existingEmployee);
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(long id) {
    Optional<Employee> optionalEmployee = employeeJpaRepository.findById(id);

    if (optionalEmployee.isPresent()) {
      employeeJpaRepository.delete(optionalEmployee.get());
      return true;
    }
    return false;
  }

}
