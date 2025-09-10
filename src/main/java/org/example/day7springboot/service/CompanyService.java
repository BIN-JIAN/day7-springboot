package org.example.day7springboot.service;

import jakarta.servlet.http.PushBuilder;
import java.util.ArrayList;
import java.util.List;
import org.example.day7springboot.entity.Company;
import org.example.day7springboot.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  public Company createCompany(Company company) {
    companyRepository.saveCompany(company);
    return company;
  }

  public Company getCompanyByName(String name) {
    return companyRepository.getCompanies().stream()
      .filter(company -> company.getName().equals(name))
      .findFirst()
      .orElse(null);
  }

  public List<Company> getCompanies() {
    return companyRepository.getCompanies();
  }

  public String updateCompany(int id, Company updatedCompany) {
    boolean updated = companyRepository.updateCompany(id, updatedCompany);
    if (updated) {
      return "Company updated successfully";
    }
    return "Company not found";
  }

  public ResponseEntity<Void> deleteCompany(int id) {
    boolean deleted = companyRepository.deleteCompany(id);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }


  public Object getAllCompanies(Integer page, Integer size) {
    if (page == null || size == null) {
      return companyRepository.getCompanies();
    }
    int startIndex = (page - 1) * size;
    if (startIndex >= companyRepository.getCompanies().size()) {
      return new ArrayList<>();
    }
    int endIndex = Math.min(startIndex + size, companyRepository.getCompanies().size());
    return companyRepository.getCompanies().subList(startIndex, endIndex);
  }

  public void clearCompanies() {
    companyRepository.clear();
  }
}


