package org.example.day7springboot.controller;

import java.util.ArrayList;
import java.util.List;
import org.example.day7springboot.entity.Company;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

  private List<Company> companies = new ArrayList<>();

  @PostMapping("/companies")
  public Company createCompany(@RequestBody Company company) {
    company.setId(companies.size() + 1);
    companies.add(company);
    return company;
  }
  @GetMapping("/companies")
  public List<Company> getAllCompanies() {
    return companies;
  }



  @GetMapping("/companies/{name}")
  public Company getCompanyByName(@PathVariable String name) {
    return companies.stream()
      .filter(company -> company.getName().equals(name))
      .findFirst()
      .orElse(null);
  }

  @PutMapping("/companies/{id}")
  public String updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) {
    for (Company company : companies) {
      if (company.getId() == id) {
        company.setName(updatedCompany.getName());
        return "Company updated successfully";
      }
    }
    return "Company not found";
  }

  @DeleteMapping("/companies/{id}")
  public String deleteCompany(@PathVariable int id) {
    for (int i = 0; i < companies.size(); i++) {
      if (companies.get(i).getId() == id) {
        companies.remove(i);
        return "Company deleted successfully";
      }
    }
    return "Company not found";
  }

  @GetMapping("/companies1")
  public Object getAllCompanies(@RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
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
}
