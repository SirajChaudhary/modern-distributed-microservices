-- CREATE DATABASE course_db;

-- CREATE TABLE
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    instructor_id BIGINT NOT NULL,
    duration_hours INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL
);

-- SAMPLE SEED DATA
INSERT INTO courses (
    title,
    description,
    instructor_id,
    duration_hours,
    active,
    created_at
)
VALUES
(
    'Spring Boot Fundamentals',
    'Learn Spring Boot from scratch',
    4,
    20,
    TRUE,
    CURRENT_TIMESTAMP
),
(
    'Microservices with Spring Cloud',
    'Build distributed systems using Spring Cloud',
    5,
    25,
    TRUE,
    CURRENT_TIMESTAMP
),
(
    'Apache Kafka for Event-Driven Systems',
    'Learn asynchronous communication using Apache Kafka',
    4,
    18,
    TRUE,
    CURRENT_TIMESTAMP
),
(
    'Spring Security and Keycloak',
    'Secure microservices using OAuth2, JWT and Keycloak',
    5,
    15,
    TRUE,
    CURRENT_TIMESTAMP
);