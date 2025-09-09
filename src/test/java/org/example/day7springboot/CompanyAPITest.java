package org.example.day7springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyAPITest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void should_create_company_when_post_valid_company() throws Exception {
    String requestBody = """
      {
        "name": "Tech"
      }
      """;

    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Tech"));
  }


  @Test
  void should_get_all_companies_when_companies_exist() throws Exception {
    String requestBody1 = """
      {
        "name": "Apple Inc"
      }
      """;
    String requestBody2 = """
      {
        "name": "Google LLC"
      }
      """;

    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody1));
    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody2));

    mockMvc.perform(get("/companies")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Apple Inc"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("Google LLC"));
  }

  @Test
  void should_get_company_by_name_when_company_exists() throws Exception {

    String requestBody = """
      {
        "name": "Microsoft"
      }
      """;
    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody));
    mockMvc.perform(get("/companies/Microsoft")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Microsoft"));
  }
  @Test
  void should_update_company_when_put_with_valid_id() throws Exception {
    String createRequestBody = """
      {
        "name":"OOCL"
      }
      """;
    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createRequestBody));

    String updateRequestBody = """
      {
        "name":"CARGO"
      }
      """;
    mockMvc.perform(put("/companies/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody))
        .andExpect(status().isOk());

    //
    mockMvc.perform(get("/companies/CARGO")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("CARGO"));
  }

  @Test
  void should_delete_company_when_delete_with_valid_id() throws Exception {
    String createRequestBody = """
      {
        "name":"OOCL"
      }
      """;
    mockMvc.perform(post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createRequestBody));

    mockMvc.perform(delete("/companies/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value("Company deleted successfully"));

    mockMvc.perform(get("/companies/OOCL")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());
  }




}
