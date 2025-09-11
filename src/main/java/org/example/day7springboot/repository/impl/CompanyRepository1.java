package org.example.day7springboot.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.example.day7springboot.entity.Company;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository1 {

  private final List<Company> companies = new ArrayList<>();

  public void saveCompany(Company company) {
    company.setId(companies.size() + 1);
    companies.add(company);
  }


  public List<Company> getCompanies() {
    return companies;
  }

  public void clear() {
    companies.clear();
  }

  public Company getCompanyById(int id) {
    return companies.stream()
      .filter(c -> c.getId() == id)
      .findFirst()
      .orElse(null);
  }

  public boolean updateCompany(int id, Company updatedCompany) {
    for (int i = 0; i < companies.size(); i++) {
      if (companies.get(i).getId() == id) {
        updatedCompany.setId(id);
        companies.set(i, updatedCompany);
        return true;
      }
    }
    return false;
  }

  public boolean deleteCompany(int id) {
    for (int i = 0; i < companies.size(); i++) {
      if (companies.get(i).getId() == id) {
        companies.remove(i);
        return true;
      }
    }
    return false;
  }
}
