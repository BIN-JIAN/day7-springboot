package org.example.day7springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

}
