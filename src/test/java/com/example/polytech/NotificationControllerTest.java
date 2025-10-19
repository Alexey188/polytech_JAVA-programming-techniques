package com.example.polytech;

import com.example.polytech.domain.Notification;
import com.example.polytech.ports.NotificationService;
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
    @Autowired NotificationService notificationService;
    ObjectMapper om = new ObjectMapper();

    @Test
    void list_pending_markRead() throws Exception {
        UUID user = UUID.randomUUID();

        Notification created = notificationService.create(
                Notification.newNotification(user, "Deadline tomorrow")
        );
        String id = created.id().toString();

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
