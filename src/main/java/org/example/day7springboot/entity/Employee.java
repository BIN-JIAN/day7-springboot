package org.example.day7springboot.entity;

public class Employee {

  private long id;
  private String name;
  private int age;
  private double salary;
  private String gender;
  private boolean status;

  public Employee(long id , String name, int age, String gender, double salary) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.salary = salary;
  }

  public Employee() {

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

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}
