package com.example.enrollment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Creates Kafka topics required by Enrollment Service.
 *
 * On application startup, Spring Boot automatically creates
 * the configured Kafka topic if it does not already exist.
 *
 * The topic can be viewed from Kafka UI:
 *
 * http://localhost:8088
 *
 * Topic:
 * enrollment-created-topic
 *
 * This topic is used to publish EnrollmentCreatedEvent
 * messages which are consumed by downstream services
 * such as Notification Service.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates the Kafka topic used for enrollment creation events.
     *
     * Partitions: 1
     * Replication Factor: 1
     */
    @Bean
    public NewTopic enrollmentCreatedTopic() {

        return new NewTopic("enrollment-created-topic", 1, (short) 1);
    }
}