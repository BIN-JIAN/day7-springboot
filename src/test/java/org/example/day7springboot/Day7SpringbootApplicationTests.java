package org.example.day7springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.day7springboot.controller.CompanyController;
import org.example.day7springboot.controller.EmployeesController;
import org.example.day7springboot.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
//清内存
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class Day7SpringbootApplicationTests {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private EmployeesController employeesController;
  @BeforeEach
  public void setUp() {
    employeesController.clearEmployee();
  }

  @Test
  void should_create_a_employee_when_post_then_return_id() throws Exception {
    String requestBody = """
                 {   
                    "name":"John",
                    "age": 30,
                    "gender": "MALE",
                    "salary": 6000
                   }
      
      """;
    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(jsonPath("$.id").value(1));

  }

  @Test
  void should_get_a_employee_when_give_a_id_then_return_a_employee() throws Exception {
    String requestBody = """
      {
        "name":"John",
        "age": 30,
        "gender": "MALE",
        "salary": 6000
      }
      """;
    mockMvc.perform(post("/employees")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody));

    mockMvc.perform(get("/employees/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("John"))
      .andExpect(jsonPath("$.age").value(30))
      .andExpect(jsonPath("$.gender").value("MALE"))
      .andExpect(jsonPath("$.salary").value(6000.0));
  }

  @Test
  void should_get_a_employee_when_give_gender_then_return_a_employee() throws Exception {
    String requestBody = """
      {
        "name":"John",
        "age": 30,
        "gender": "male",
        "salary": 6000
      }
      """;
    mockMvc.perform(post("/employees")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody));
    mockMvc.perform(get("/employees/gender?gender=male")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].name").value("John"))
      .andExpect(jsonPath("$[0].age").value(30))
      .andExpect(jsonPath("$[0].gender").value("male"))
      .andExpect(jsonPath("$[0].salary").value(6000.0));
  }

  @Test
  void should_get_all_employees_when_call_get_all() throws Exception {
    String requestBody1 = """
      {
        "name":"John",
        "age": 30,
        "gender": "MALE",
        "salary": 6000
      }
      """;
    String requestBody2 = """
      {
        "name":"Alice",
        "age": 28,
        "gender": "FEMALE",
        "salary": 7000
      }
      """;
    mockMvc.perform(post("/employees")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody1));
    mockMvc.perform(post("/employees")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody2));

    mockMvc.perform(get("/employees")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].name").value("John"))
      .andExpect(jsonPath("$[1].id").value(2))
      .andExpect(jsonPath("$[1].name").value("Alice"));
  }

  @Test
  void should_update_employee_when_put_with_valid_id() throws Exception {
    String createRequestBody = """
      {
        "name":"John",
        "age": 30,
        "gender": "MALE",
        "salary": 6000
      }
      """;
    mockMvc.perform(post("/employees")
      .contentType(MediaType.APPLICATION_JSON)
      .content(createRequestBody));

    String updateRequestBody = """
      {
        "name":"John Smith",
        "age": 35,
        "gender": "MALE",
        "salary": 8000
      }
      """;
    mockMvc.perform(put("/employees/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody))
      .andExpect(status().isNoContent());

    mockMvc.perform(get("/employees/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("John Smith"))
      .andExpect(jsonPath("$.age").value(35))
      .andExpect(jsonPath("$.gender").value("MALE"))
      .andExpect(jsonPath("$.salary").value(8000.0));
  }

  @Test
  void should_delete_employee_when_delete_with_valid_id() throws Exception {
    String createRequestBody = """
      {
        "name":"John",
        "age": 30,
        "gender": "MALE",
        "salary": 6000
      }
      """;
    mockMvc.perform(post("/employees")
      .contentType(MediaType.APPLICATION_JSON)
      .content(createRequestBody));

    mockMvc.perform(delete("/employees/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    mockMvc.perform(get("/employees/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").doesNotExist());
  }

  @Test
  void should_get_paginated_employees_with_page_info_employees1() throws Exception {
    // 创建5个员工用于分页测试
    for (int i = 1; i <= 5; i++) {
      String requestBody = String.format("""
        {
          "name":"Employee%d",
          "age": %d,
          "gender": "MALE",
          "salary": %d
        }
        """, i, 20 + i, 5000);
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
    for (int i = 1; i <= 2; i++) {
      String requestBody = String.format("""
        {
          "name":"Employee%d",
          "age": 25,
          "gender": "MALE",
          "salary": 5000
        }
        """, i);
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

  }

