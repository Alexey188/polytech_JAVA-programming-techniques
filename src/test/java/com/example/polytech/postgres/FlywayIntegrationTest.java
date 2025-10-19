package com.example.polytech.postgres;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("postgres")
class FlywayIntegrationTest {
    @Test
    void contextLoadsWithFlyway() { }
}
