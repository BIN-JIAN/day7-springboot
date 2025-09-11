package org.example.day7springboot.repository;

import java.util.List;
import org.example.day7springboot.entity.Company;

public interface CompanyRepository {

  void saveCompany(Company company);

  List<Company> getCompanies();

  Company getCompanyById(long id);

  boolean updateCompany(long id, Company updatedCompany);

  boolean deleteCompany(long id);

}
