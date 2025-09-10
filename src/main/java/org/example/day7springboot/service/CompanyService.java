package org.example.day7springboot.service;

import jakarta.servlet.http.PushBuilder;
import java.util.ArrayList;
import java.util.List;
import org.example.day7springboot.entity.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CompanyService {
  private List<Company> companies = new ArrayList<>();

  public Company createCompany(Company company) {
    company.setId(companies.size() + 1);
    companies.add(company);
     return company;
  }

  public Company getCompanyByName(String name){
    return companies.stream()
      .filter(company -> company.getName().equals(name))
      .findFirst()
      .orElse(null);
  }
  public List<Company> getCompanies() {
    return companies;
  }

  public String updateCompany(int id,  Company updatedCompany){
    for (Company company : companies) {
      if (company.getId() == id) {
        company.setName(updatedCompany.getName());
        return "Company updated successfully";
      }
    }
    return "Company not found";
  }

  public ResponseEntity<Void> deleteCompany( int id) {
    for (int i = 0; i < companies.size(); i++) {
      if (companies.get(i).getId() == id) {
        companies.remove(i);
        return ResponseEntity.noContent().build();
      }
    }
    return ResponseEntity.notFound().build();
  }

  public Object getAllCompanies(Integer page, Integer size) {
    if (page == null || size == null) {
      return companies;
    }
    int startIndex = (page - 1) * size;
    if (startIndex >= companies.size()) {
      return new ArrayList<>();
    }
    int endIndex = Math.min(startIndex + size, companies.size());
    return companies.subList(startIndex, endIndex);
  }

  public void clearCompanies() {
    companies.clear();
  }
}

