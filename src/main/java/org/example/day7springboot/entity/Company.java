package org.example.day7springboot.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_company")
public class Company {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private List<Employee> employees = new ArrayList<>();

  public Company(String cosco) {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getEmployees() {
    return null;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }
}
