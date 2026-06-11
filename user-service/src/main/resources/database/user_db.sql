-- CREATE DATABASE user_db;

-- CREATE TABLE
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- SAMPLE SEED DATA
INSERT INTO users (first_name, last_name, email, role)
VALUES
('Amit', 'Sharma', 'amit.sharma@example.com', 'STUDENT'),
('Priya', 'Verma', 'priya.verma@example.com', 'STUDENT'),
('Rahul', 'Patel', 'rahul.patel@example.com', 'STUDENT'),

('Neha', 'Kulkarni', 'neha.kulkarni@example.com', 'INSTRUCTOR'),
('Arjun', 'Reddy', 'arjun.reddy@example.com', 'INSTRUCTOR'),

('Vikram', 'Singh', 'vikram.singh@example.com', 'ADMIN');