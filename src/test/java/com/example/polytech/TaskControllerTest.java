package com.example.polytech;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("inmem")
class TaskControllerTest {
    @Autowired MockMvc mvc;
    ObjectMapper om = new ObjectMapper();

    @Test
    void create_list_pending_softDelete() throws Exception {
        UUID user = UUID.randomUUID();
        String body = """
      {"title":"Read","description":"ch1","targetDate":"2030-01-01T00:00:00Z"}
      """;

        var created = mvc.perform(post("/api/v1/users/"+user+"/tasks")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.targetDate").value("2030-01-01T00:00:00Z"))
                .andReturn();

        String id = om.readTree(created.getResponse().getContentAsString()).get("id").asText();

        mvc.perform(get("/api/v1/users/"+user+"/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Read"));

        mvc.perform(get("/api/v1/users/"+user+"/tasks/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Read"));

        mvc.perform(delete("/api/v1/users/"+user+"/tasks/"+id))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/v1/users/"+user+"/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
