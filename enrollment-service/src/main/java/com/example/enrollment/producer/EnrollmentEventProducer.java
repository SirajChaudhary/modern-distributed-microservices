package com.example.enrollment.producer;

import com.example.common.event.EnrollmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Publishes enrollment-related events to Kafka.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EnrollmentEventProducer {

    private static final String TOPIC = "enrollment-created-topic";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Publishes EnrollmentCreatedEvent.
     */
    public void publishEnrollmentCreatedEvent(
            EnrollmentCreatedEvent event) {

        log.info("Publishing EnrollmentCreatedEvent enrollmentId={}", event.enrollmentId());

        kafkaTemplate.send(TOPIC, event);
    }
}