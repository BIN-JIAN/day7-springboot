package org.example.day7springboot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.day7springboot.entity.Company;
import org.example.day7springboot.repository.dao.CompanyJpaRepository;
import org.example.day7springboot.repository.dao.EmployeeJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
//清内存
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private EmployeeJpaRepository employeeJpaRepository;
  @Autowired
  private CompanyJpaRepository companyRepository;
  @AfterEach
  void cleanDatabase() {
    employeeJpaRepository.deleteAll();
  }

  @Test
  void should_create_a_employee_when_post_then_return_id() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    String requestBody = String.format("""
                 {   
                    "name":"25hn",
                    "age": 30,
                    "gender": "MALE",
                    "salary": 60000,
                    "companyId": %d
                   }
      """, company.getId());
    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isOk());
  }

  @Test
  void should_get_a_employee_when_give_a_id_then_return_a_employee() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name": "2wqeqJohn",
        "age": 30,
        "gender": "MALE",
        "salary": 60000.0,
        "status": true,
        "companyId": %d
      }
      """, company.getId());
    long id = createEmployee(requestBody);
    mockMvc.perform(get("/employees/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(id))
      .andExpect(jsonPath("$.name").value("2wqeqJohn"))
      .andExpect(jsonPath("$.age").value(30))
      .andExpect(jsonPath("$.gender").value("MALE"))
      .andExpect(jsonPath("$.salary").value(60000.0));
  }

  @Test
  void should_get_a_employee_when_give_gender_then_return_a_employee() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name":"John",
        "age": 30,
        "gender": "male",
        "salary": 60000,
        "companyId": %d
      }
      """, company.getId());
    long id = createEmployee(requestBody);
    mockMvc.perform(get("/employees/gender?gender=male")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(id))
      .andExpect(jsonPath("$[0].name").value("John"))
      .andExpect(jsonPath("$[0].age").value(30))
      .andExpect(jsonPath("$[0].gender").value("male"))
      .andExpect(jsonPath("$[0].salary").value(60000));
  }

  @Test
  void should_get_all_employees_when_call_get_all() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    String requestBody1 = String.format("""
      {
        "name":"John",
        "age": 30,
        "gender": "MALE",
        "salary": 36000,
        "companyId": %d
      }
      """, company.getId());
    String requestBody2 = String.format("""
      {
        "name":"Alice",
        "age": 28,
        "gender": "FEMALE",
        "salary": 7000,
        "companyId": %d
      }
      """, company.getId());
    long id1 = createEmployee(requestBody1);
    long id2 = createEmployee(requestBody2);
    mockMvc.perform(get("/employees")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(id1))
      .andExpect(jsonPath("$[0].name").value("John"))
      .andExpect(jsonPath("$[1].id").value(id2))
      .andExpect(jsonPath("$[1].name").value("Alice"));
  }

  @Test
  void should_update_employee_when_put_with_valid_id() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    String createRequestBody = String.format("""
      {
        "name":"20",
        "age": 30,
        "gender": "MALE",
        "salary": 60000,
        "status": true,
        "companyId": %d
      }
      """, company.getId());
    long id = createEmployee(createRequestBody);
    String updateRequestBody = String.format("""
      {
        "name":"John Smith",
        "age": 35,
        "gender": "MALE",
        "salary": 80000,
        "status": true,
        "companyId": %d
      }
      """, company.getId());
    mockMvc.perform(put("/employees/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody))
      .andExpect(status().isNoContent());

    mockMvc.perform(get("/employees/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(id))
      .andExpect(jsonPath("$.name").value("John Smith"))
      .andExpect(jsonPath("$.age").value(35))
      .andExpect(jsonPath("$.gender").value("MALE"))
      .andExpect(jsonPath("$.salary").value(80000));
  }

  @Test
  void should_delete_employee_when_delete_with_valid_id() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    String createRequestBody = String.format("""
      {
        "name":"20",
        "age": 30,
        "gender": "MALE",
        "salary": 60000,
        "status": true,
        "companyId": %d
      }
      """, company.getId());
    long id = createEmployee(createRequestBody);

    mockMvc.perform(delete("/employees/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    mockMvc.perform(get("/employees/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").doesNotExist());
  }

  @Test
  void should_get_paginated_employees_with_page_info_employees1() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    // 创建5个员工用于分页测试
    for (int i = 1; i <= 5; i++) {
      String requestBody = String.format("""
        {
          "name":"Employee%d",
          "age": %d,
          "gender": "MALE",
          "salary": %d,
          "companyId": %d
        }
        """, i, 20 + i, 5000, company.getId());
      mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody));
    }

    // 测试第一页，每页3条数据
    mockMvc.perform(get("/employees1?page=1&size=3")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$.length()").value(3))
      .andExpect(jsonPath("$[0].name").value("Employee1"))
      .andExpect(jsonPath("$[1].name").value("Employee2"))
      .andExpect(jsonPath("$[2].name").value("Employee3"));

    // 测试第二页，每页3条数据（应该只有2条）
    mockMvc.perform(get("/employees1?page=2&size=3")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$.length()").value(2))
      .andExpect(jsonPath("$[0].name").value("Employee4"))
      .andExpect(jsonPath("$[1].name").value("Employee5"));
  }

  @Test
  void should_return_empty_data_when_page_exceeds_total_employees1() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    for (int i = 1; i <= 2; i++) {
      String requestBody = String.format("""
        {
          "name":"Employee%d",
          "age": 25,
          "gender": "MALE",
          "salary": 5000,
          "companyId": %d
        }
        """, i, company.getId());
      mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody));
    }
    //
    mockMvc.perform(get("/employees1?page=5&size=3")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void should_age_exceed_30_and_salary_below_20000_when_post_then_not_create() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    for (int i = 1; i <= 2; i++) {
      String requestBody = String.format("""
        {
          "name":"Employee%d",
          "age": 35,
          "gender": "MALE",
          "salary": 5000,
          "companyId": %d
        }
        """, i, company.getId());
      mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isBadRequest());
    }
  }

  @Test
  void should_not_create_employee_with_same_name_and_gender() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name":"Tom",
        "age":30,
        "gender":"MALE",
        "salary":25000,
        "companyId": %d
      }
      """, company.getId());
    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isOk());

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isBadRequest());
  }

  @Test
  void should_create_employee_with_same_name_and_different_gender_then_pass() throws Exception {
    Company company = new Company("TestCompany");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name":"Tom",
        "age":30,
        "gender":"MALE",
        "salary":25000,
        "companyId": %d
      }
      """, company.getId());
    String requestBody1 = String.format("""
      {
        "name":"Tom",
        "age":30,
        "gender":"FEMALE",
        "salary":25000,
        "companyId": %d
      }
      """, company.getId());
    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isOk());

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody1))
      .andExpect(status().isOk());
  }

  @Test
  void should_delete_employee_when_status_true_then_success() throws Exception {
    Company company = new Company("cosco");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name": "Tom",
        "age": 30,
        "gender": "MALE",
        "salary": 25000,
        "status": true,
        "companyId": %d
      }
      """, company.getId());
    long employeeId = createEmployee(requestBody);
    mockMvc.perform(delete("/employees/{id}", employeeId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());
  }

  @Test
  void should_not_delete_employee_when_status_false_then_bad_request() throws Exception {
    Company company = new Company("cosco");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name": "Tom",
        "age": 30,
        "gender": "MALE",
        "salary": 25000,
        "status": false,
        "companyId": %d
      }
      """, company.getId());
    long employeeId = createEmployee(requestBody);
    mockMvc.perform(delete("/employees/{id}", employeeId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  void should_update_employee_when_status_true_and_salary_above_20000_then_success() throws Exception {
    Company company = new Company("cosco");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name": "Tom",
        "age": 30,
        "gender": "MALE",
        "salary": 25000,
        "status": true,
        "companyId": %d
      }
      """, company.getId());
    long employeeId = createEmployee(requestBody);
    String updateRequestBody = """
      {
        "name":"Tom",
        "age":35,
        "gender":"MALE",
        "salary":25000
      }
      """;
    mockMvc.perform(put("/employees/{id}",  employeeId )
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody))
      .andExpect(status().isNoContent());
  }

  @Test
  void should_not_update_employee_when_status_true_and_salary_below_20000_and_age_above_30_then_bad_request() throws Exception {
    Company company = new Company("cosco");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name": "Tom",
        "age": 30,
        "gender": "MALE",
        "salary": 25000,
        "status": true,
        "companyId": %d
      }
      """, company.getId());
    long employeeId = createEmployee(requestBody);
    String updateRequestBody = """
      {
        "name":"Tom",
        "age":35,
        "gender":"MALE",
        "salary":15000,
        "status":true
      }
      """;
    mockMvc.perform(put("/employees/{id}", employeeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody))
      .andExpect(status().isBadRequest());
  }

  @Test
  void should_not_update_employee_when_status_false_then_bad_request() throws Exception {
    Company company = new Company("cosco");
    companyRepository.save(company);
    String requestBody = String.format("""
      {
        "name": "Tom",
        "age": 30,
        "gender": "MALE",
        "salary": 25000,
        "status": false,
        "companyId": %d
      }
      """, company.getId());
    long employeeId = createEmployee(requestBody);
    String updateRequestBody = """
      {
        "name":"Tom",
        "age":35,
        "gender":"MALE",
        "salary":25000.0,
        "status":false
      }
      """;
    mockMvc.perform(put("/employees/{id}", employeeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody))
      .andExpect(status().isBadRequest());
  }

  private long createEmployee(String requestBody) throws Exception {
    ResultActions resultActions = mockMvc.perform(post("/employees")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody));

    MvcResult mvcResult = resultActions.andReturn();

    String contentAsString = mvcResult.getResponse().getContentAsString();

    return new ObjectMapper().readTree(contentAsString).get("id").asLong();
  }

}
