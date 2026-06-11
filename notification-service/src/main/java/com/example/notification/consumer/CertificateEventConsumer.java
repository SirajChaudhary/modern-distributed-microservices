package com.example.notification.consumer;

import com.example.common.event.CertificateGeneratedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumes certificate-related Kafka events.
 */
@Slf4j
@Component
public class CertificateEventConsumer {

    /**
     * Consumes CertificateGeneratedEvent and simulates
     * sending a certificate notification.
     */
    @KafkaListener(
            topics = "certificate-generated-topic",
            groupId = "notification-group"
    )
    public void consumeCertificateGeneratedEvent(
            CertificateGeneratedEvent event) {

        log.info(
                "Certificate notification sent certificateId={} userId={} courseId={}",
                event.certificateId(),
                event.userId(),
                event.courseId()
        );
    }
}