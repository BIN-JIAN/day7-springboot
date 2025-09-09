package org.example.day7springboot.entity;

public class Employee {

  private long id;
  private String namae;
  private int age;
  private double salary;
  private String gender;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNamae() {
    return namae;
  }

  public void setNamae(String namae) {
    this.namae = namae;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }
}
