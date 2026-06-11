package com.example.certificate.producer;

import com.example.common.event.CertificateGeneratedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Publishes certificate-related events to Kafka.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CertificateEventProducer {

    private static final String TOPIC = "certificate-generated-topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Publishes CertificateGeneratedEvent.
     */
    public void publishCertificateGeneratedEvent(
            CertificateGeneratedEvent event) {

        log.info("Publishing CertificateGeneratedEvent certificateId={}", event.certificateId());

        kafkaTemplate.send(TOPIC, event);
    }
}