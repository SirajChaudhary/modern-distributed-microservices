package com.example.notification.consumer;

import com.example.common.event.EnrollmentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumes enrollment-related Kafka events.
 */
@Slf4j
@Component
public class EnrollmentEventConsumer {

    /**
     * Consumes EnrollmentCreatedEvent and simulates
     * sending an enrollment notification.
     */
    @KafkaListener(
            topics = "enrollment-created-topic",
            groupId = "notification-group"
    )
    public void consumeEnrollmentCreatedEvent(
            EnrollmentCreatedEvent event) {

        log.info(
                "Enrollment notification sent enrollmentId={} userId={} courseId={}",
                event.enrollmentId(),
                event.userId(),
                event.courseId()
        );
    }
}