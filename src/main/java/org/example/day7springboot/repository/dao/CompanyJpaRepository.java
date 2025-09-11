package org.example.day7springboot.repository.dao;

import org.example.day7springboot.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company,Long> {

}
