package org.example.day7springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.day7springboot.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class Day7SpringbootApplicationTests {
  @Autowired
  private MockMvc mockMvc;

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


}
