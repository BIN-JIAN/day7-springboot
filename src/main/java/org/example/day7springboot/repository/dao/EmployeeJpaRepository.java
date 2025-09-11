package org.example.day7springboot.repository.dao;

import org.example.day7springboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository extends JpaRepository<Employee,Long> {

}
