package org.example.day7springboot.controller;

import java.util.List;
import org.example.day7springboot.entity.Company;
import org.example.day7springboot.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  private CompanyService companyService;

  @PostMapping("/companies")
  public Company createCompany(@RequestBody Company company) {
    return companyService.createCompany(company);
  }

  @GetMapping("/companies")
  public List<Company> getAllCompanies() {
    return companyService.getCompanies();
  }

  @GetMapping("/companies/{id}")
  public Company getCompanyById(@PathVariable long id) {
    return companyService.getCompanyById(id);
  }

  //
  @PutMapping("/companies/{id}")
  public String updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) {
    return companyService.updateCompany(id, updatedCompany);
  }

  //
  @DeleteMapping("/companies/{id}")
  public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
    return companyService.deleteCompany(id);
  }

  //  //该类型 加一个异常处理器
//
  @GetMapping("/companies1")
  public Object getAllCompanies(
    @RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
    return companyService.getAllCompanies(page, size);
  }


}
