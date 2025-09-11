package org.example.day7springboot.repository.impl;

import java.util.List;
import java.util.Optional;
import org.example.day7springboot.entity.Company;
import org.example.day7springboot.entity.Employee;
import org.example.day7springboot.repository.CompanyRepository;
import org.example.day7springboot.repository.dao.CompanyJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

  @Autowired
  private CompanyJpaRepository companyJpaRepository;
  public void saveCompany(Company company) {
    companyJpaRepository.save(company);
  }

  public List<Company> getCompanies() {
    return companyJpaRepository.findAll();
  }

  public Company getCompanyById(long id) {
   return companyJpaRepository.findById(id).orElse(null);
  }

  public boolean updateCompany(long id, Company updatedCompany) {
    Optional<Company> optionalEmployee = companyJpaRepository.findById(id);

    if (optionalEmployee.isPresent()) {
      Company existingEmployee = optionalEmployee.get();
      existingEmployee.setName(existingEmployee.getName());
      companyJpaRepository.save(existingEmployee);
      return true;
    }
    return false;
  }

  public boolean deleteCompany(long id) {
    Optional<Company> optionalEmployee = companyJpaRepository.findById(id);

    if (optionalEmployee.isPresent()) {
      companyJpaRepository.delete(optionalEmployee.get());
      return true;
    }
    return false;
  }

}
