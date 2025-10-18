package com.example.polytech;

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
class NotificationControllerTest {
    @Autowired MockMvc mvc;
    ObjectMapper om = new ObjectMapper();

    @Test
    void create_list_pending_markRead() throws Exception {
        UUID user = UUID.randomUUID();

        var created = mvc.perform(post("/api/v1/users/"+user+"/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Deadline tomorrow\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.read").value(false))
                .andReturn();

        String id = om.readTree(created.getResponse().getContentAsString()).get("id").asText();

        mvc.perform(get("/api/v1/users/"+user+"/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Deadline tomorrow"));

        mvc.perform(get("/api/v1/users/"+user+"/notifications/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].read").value(false));

        mvc.perform(put("/api/v1/users/"+user+"/notifications/"+id+"/read"))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/v1/users/"+user+"/notifications/pending"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
