package com.example.polytech.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic taskCreatedEventsTopic() {
        return TopicBuilder.name("task-created-events")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
