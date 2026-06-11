# Modern Distributed Microservices

# Overview

As modern applications grow, different business capabilities often need to scale, evolve, and fail independently. At the same time, some operations require immediate responses while others can be processed asynchronously in the background. Designing a system that balances responsiveness, scalability, fault tolerance, and loose coupling is a common challenge in enterprise software development.

To address these requirements, I built this project to simulate an enterprise-grade eLearning Platform where users such as Students, Instructors, and Administrators can manage courses, enroll in learning programs, generate certificates, and receive notifications related to enrollment and certificate generation.

Some business operations require real-time validation and immediate responses. For example, an enrollment request must validate both the user and the course before it can be created. Other operations, such as certificate generation and notification processing, can be executed asynchronously after enrollment is completed.

To support these requirements, the platform uses a hybrid communication model:

- Synchronous communication using OpenFeign for real-time service-to-service interactions
- Asynchronous communication using Apache Kafka for event-driven workflows

The system demonstrates how enterprise applications achieve scalability, maintainability, fault isolation, deployment flexibility, and eventual consistency by combining synchronous and event-driven communication patterns within a distributed microservices architecture.


Business Flow:

```text
Student Registers
        ↓
Browse Courses
        ↓
Enroll Into Course
        ↓
Enrollment Created
        ↓
Certificate Generated
        ↓
Notification Sent
```

# Architecture

```text
                                        +----------------------+
                                        |      Keycloak        |
                                        | OAuth2 / JWT / RBAC  |
                                        +----------+-----------+
                                                   |
                                                   |
                                                   v

+------------+      +---------------------------------------------------+
|   Client   |----->|                   API Gateway                     |
+------------+      +---------------------------------------------------+
                    |                                                   |
                    | Responsibilities                                  |
                    | ------------------------------------------------- |
                    | • Request Routing                                 |
                    | • JWT Validation                                  |
                    | • Authentication                                  |
                    | • Authorization (RBAC)                            |
                    | • Redis Rate Limiting                             |
                    | • Correlation ID Propagation                      |
                    | • Request Logging                                 |
                    | • Service Discovery Integration                   |
                    +----------------------+----------------------------+
                                           |
                                           |
                                           v

                            +-------------------------------+
                            |      Eureka Discovery         |
                            +-------------------------------+
                                           |
                                           |
    -----------------------------------------------------------------------------------
    |                          |                          |                           |
    |                          |                          |                           |
    v                          v                          v                           v

+----------------+   +----------------+   +----------------------+   +----------------------+
|  User Service  |   | Course Service |   | Enrollment Service   |   | Certificate Service  |
+----------------+   +----------------+   +----------------------+   +----------------------+
        |                    |               |         |                            |
        v                    v               v         |                            v
                                                       |
    +--------+          +--------+       +--------+    |                        +--------+
    |   DB   |          |   DB   |       |   DB   |    |                        |   DB   |
    +--------+          +--------+       +--------+    |                        +--------+
                                                       |
                                                       |
                                                       | It publish to a kafka topic
                                                       v

                                           +----------------------+
                                           |     Apache Kafka     |
                                           +----------+-----------+
                                                       | 
                                                       | Publish
                                                       v

                                       enrollment-created-topic (Kafka Topic)
                                                       |
                                                       |
                     --------------------------------------------------
                     |                                                |
                     | Consume                                        | Consume
                     v                                                v

          +----------------------+                        +----------------------+
          | Certificate Service  |                        | Notification Service |
          +----------+-----------+                        +----------------------+
                     |
                     |
                     | Publish
                     | 
                     v

   certificate-generated-topic (Kafka Topic)
                     |
                     |
                     | Consume
                     v

          +----------------------+
          | Notification Service |
          +----------------------+





+----------------------+                          +----------------------+
|        Redis         |<------------------------>|     API Gateway      |
|    Rate Limiting     |                          +----------------------+
+----------------------+





+--------------------------------------------------------------------------+
|                                Zipkin                                    |
|      Distributed Tracing / TraceId / SpanId Monitoring                   |
+--------------------------------------------------------------------------+
        ^           ^             ^             ^             ^
        |           |             |             |             |
        +-----------+-------------+-------------+-------------+
                    Gateway and all Microservices
```
This project demonstrates:

- Microservices Architecture
- API Gateway Pattern
- Service Discovery Pattern
- Database-per-Service Pattern
- Event-Driven Architecture
- Publish-Subscribe Pattern
- OAuth2 Security Pattern
- JWT Authentication
- Role-Based Access Control (RBAC)
- Circuit Breaker Pattern
- Retry Pattern
- Fallback Pattern
- Distributed Tracing Pattern
- Rate Limiting Pattern

# Technology Stack

| Category | Technology | Purpose |
|-----------|------------|----------|
| Programming Language | Java 21 | Application Development |
| Application Framework | Spring Boot  | Microservice Development |
| Cloud Framework | Spring Cloud | Distributed System Components |
| API Gateway | Spring Cloud Gateway | Request Routing, Security, Rate Limiting |
| Service Discovery | Eureka | Service Registration & Discovery |
| Service-to-Service Communication | OpenFeign | Synchronous Service Communication |
| Security Framework | Spring Security | API Security |
| Identity & Access Management | Keycloak | Authentication & Authorization |
| Authentication Protocol | OAuth2, JWT | Secure API Access |
| Event Streaming Platform | Apache Kafka | Asynchronous Communication |
| Kafka Integration | Spring Kafka | Kafka Producer & Consumer Support |
| Database | PostgreSQL | Persistent Storage |
| Persistence Layer | Spring Data JPA, Hibernate | Data Access & ORM |
| Validation | Jakarta Bean Validation | Request Validation |
| Caching & Rate Limiting | Redis | Distributed Rate Limiting |
| Fault Tolerance | Resilience4j | Circuit Breaker, Retry, Fallback |
| Observability | Micrometer, OpenTelemetry | Metrics & Distributed Tracing |
| Trace Visualization | Zipkin | Trace Monitoring & Analysis |
| API Documentation | OpenAPI, Swagger UI | API Documentation |
| Build Tool | Maven | Build & Dependency Management |
| Boilerplate Reduction | Lombok | Code Simplification |

# Project Structure

```text
modern-distributed-microservices
│
├── common-library
|
├── discovery-service
├── gateway-service
|
├── user-service
├── course-service
├── enrollment-service
├── certificate-service
├── notification-service
│
├── README.md
├── keycloak.md
│
└── pom.xml (Parent POM used to build all modules with a single command)
```

# Why Microservices?

As the eLearning platform grows, different business capabilities need to scale independently.

Examples:

- User Management
- Course Management
- Enrollment Processing
- Certificate Generation
- Notification Processing

Separating these domains into independent services improves:

- Scalability
- Maintainability
- Deployment Independence
- Fault Isolation
- Team Autonomy


# Microservices

### 1. Gateway Service

Acts as the single entry point for all client requests entering the platform.

Responsibilities
- Request Routing
- JWT Validation
- Authentication
- Authorization (RBAC)
- Redis Rate Limiting
- Correlation ID Propagation
- Request Logging
- Service Discovery Integration

Technologies Used
- Spring Cloud Gateway
- Spring Security
- Keycloak
- Redis
- Eureka Client

### 2. Discovery Service

Acts as the service registry for the platform.

Responsibilities
- Service Registration
- Service Discovery
- Dynamic Endpoint Resolution

Technology Used
- Netflix Eureka Server

Registered Services
- gateway-service
- user-service
- course-service
- enrollment-service
- certificate-service
- notification-service

### 3. User Service

Responsible for user (E.g. Student, Instructor, Admin) management.

Features

- Create User
- Get User By Id
- Get All Users
- Update User
- Delete User

Database: `user_db`

### 4. Course Service

Responsible for course management.

Features

- Create Course
- Get Course By Id
- Get All Courses
- Update Course
- Delete Course

Database: `course_db`

### 5. Enrollment Service

Responsible for enrollment creation and enrollment event publishing.

Features

- Validate User
- Validate Course
- Create Enrollment
- Publish Enrollment Event

**Synchronous Communication**

Implemented using OpenFeign.

Calls:

```text
User Service
Course Service
```

**Asynchronous Communication**

Publishes:

```text
EnrollmentCreatedEvent
```

Kafka Topic:

```text
enrollment-created-topic
```

Database: `enrollment_db`

### 6. Certificate Service

Responsible for certificate generation.

Features

- Consume Enrollment Events
- Generate Certificates
- Persist Certificates
- Publish Certificate Events

Consumes:

```text
enrollment-created-topic
```

Publishes:

```text
certificate-generated-topic
```

Database: `certificate_db`

### 7. Notification Service

Responsible for consuming business events and sending user notifications.

Features

- Consume Enrollment Events
- Consume Certificate Events
- Send Notifications
- Log Notification Activity

Consumes:

```text
enrollment-created-topic 
certificate-generated-topic
```

### 8. Common Library

Shared module used by all services.

Responsibilities
- Kafka Event Contracts
- Shared DTOs
- Common Response Models
- Shared Utility Classes
- Reusable Components

Examples
- EnrollmentCreatedEvent
- CertificateGeneratedEvent
- ApiResponse
- ErrorResponse

Benefits
- Avoids Code Duplication
- Consistent Contracts Across Services
- Easier Maintenance
- Reusable Components
- Centralized Shared Models

# Infrastructure Components

# API Gateway

The API Gateway acts as the single entry point for all client requests entering the system.

Clients never communicate directly with backend microservices. Every request first passes through the Gateway.

**Responsibilities:**
- Request Routing
- JWT Validation
- Authentication
- Authorization (RBAC)
- Redis Rate Limiting
- Request Logging
- Correlation ID Propagation
- Service Discovery Integration

**Request Routing**

Routes incoming requests to the appropriate downstream microservice.

Examples:

```text
/api/v1/users/**         -> User Service
/api/v1/courses/**       -> Course Service
/api/v1/enrollments/**   -> Enrollment Service
/api/v1/certificates/**  -> Certificate Service
```

**Authentication**

Implemented using:

- Spring Security
- OAuth2 Resource Server
- JWT Validation

JWT tokens issued by Keycloak are validated at the Gateway layer before requests are forwarded to downstream services.
- Verify token signature
- Verify token expiration
- Verify token issuer
- Reject invalid requests

**Authorization (RBAC)**

Role-Based Access Control (RBAC) is implemented using Keycloak realm roles.

Enforces Role-Based Access Control using roles present inside JWT tokens.

Supported roles:

```text
ADMIN
INSTRUCTOR
STUDENT
```

**Rate Limiting**

Implemented using:

- Spring Cloud Gateway
- Redis
- RequestRateLimiter

Example Configuration:

```yaml
redis-rate-limiter.replenishRate: 1
redis-rate-limiter.burstCapacity: 2
```

*replenishRate:*

Maximum number of requests allowed per second.

*burstCapacity:*

Maximum number of requests that can be handled immediately during a temporary traffic spike.

Protects against:

- API Abuse
- Bot Traffic
- Accidental Flooding
- DoS-like Bursts

**Service Discovery Integration**

Uses Eureka to dynamically discover available service instances.

Benefits:

- No hardcoded service URLs
- Dynamic service registration
- Better scalability

**Observability**

Gateway generates and propagates:

```text
CorrelationId
TraceId
SpanId
```

which allows end-to-end request tracking across all microservices.


# Service Discovery - Eureka

Eureka acts as the service registry for all microservices.

Responsibilities:

- Dynamic Service Registration
- Dynamic Service Discovery
- Load Balancing Support

Registered Services:

```text
gateway-service
user-service
course-service
enrollment-service
certificate-service
notification-service
```

# Security - Keycloak

Keycloak provides centralized authentication and authorization.

Implemented Features:

- OAuth2
- OpenID Connect
- JWT Tokens
- User Management
- Client Management
- Role Management
- Role-Based Access Control

Configured Realm:

```text
modern-distributed-microservices
```

Roles:

```text
ADMIN
INSTRUCTOR
STUDENT
```

Detailed setup guide:

```text
docs/keycloak.md
```

# Apache Kafka

Provides asynchronous event-driven communication.

Topics

```text
enrollment-created-topic
certificate-generated-topic
```

Benefits

- Loose Coupling
- High Throughput
- Scalability
- Reliability

# Redis

Used by the Gateway for rate limiting.

Benefits

- API Protection
- Traffic Control
- Request Throttling
- Abuse Prevention

# PostgreSQL

Primary relational database used by microservices.

Pattern:

```text
Database Per Service
```


# Database Per Service Pattern

Each microservice owns its own database.

```text
User Service         -> user_db
Course Service       -> course_db
Enrollment Service   -> enrollment_db
Certificate Service  -> certificate_db
```

Benefits:

- Independent Scaling
- Better Isolation
- Service Autonomy
- Reduced Coupling


# Fault Tolerance

Implemented using Resilience4j.

Features:

- Circuit Breaker
- Retry
- Fallback Methods

Benefits:

- Fault Isolation
- Graceful Degradation
- Improved Reliability


# Distributed Tracing and Observability

Implemented using:

- Micrometer
- OpenTelemetry
- Zipkin

Features:

- CorrelationId Propagation
- TraceId Propagation
- SpanId Propagation
- Distributed Tracing
- Request Monitoring

Example Flow:

```text
Gateway
   ↓
Enrollment Service
   ↓
Certificate Service
   ↓
Notification Service
```

All requests can be traced end-to-end using Zipkin.


# Zipkin

Provides distributed tracing across services.

Benefits:

- End-to-End Request Tracking
- Latency Analysis
- Performance Monitoring

# Security Architecture

Authentication Flow:

```text
Client
   |
   | Login
   v
Keycloak
   |
   | JWT Access Token
   v
Client
   |
   | Bearer Token
   v
API Gateway
   |
   | Validate JWT
   v
Microservices
```

Authorization Flow:

```text
JWT Token
     |
     v
Realm Roles
     |
     v
Gateway Security Layer
     |
     v
Role Based Authorization
```

# Event-Driven Architecture

The platform uses Apache Kafka for asynchronous communication.

Event Flow:

```text
Enrollment Service
        |
        v
enrollment-created-topic (Kafka Topic)
        |
        +---------------------------+
        |                           |
        v                           v

Certificate Service          Notification Service
        |
        v
certificate-generated-topic (Kafka Topic)
        |
        v
Notification Service

```

Benefits:

- Loose Coupling
- Better Scalability
- Improved Reliability
- Eventual Consistency


# How to Run

Follow the steps below to run the complete platform locally.

### Step 1: Clone the Repository

```
git clone https://github.com/SirajChaudhary/modern-distributed-microservices.git

cd modern-distributed-microservices
```

### Step 2: Start all required infrastructure services using Docker Compose.

```
docker-compose up -d
```

This will start:
- Apache Kafka
- Apache Kafka UI
- Zookeeper
- Keycloak
- Redis
- Zipkin

Verify the containers are running:

```
docker ps
```

<img width="3412" height="654" alt="image" src="https://github.com/user-attachments/assets/a5fb81d6-27d2-4844-a8e0-c2286b39a5f6" />

### Step 3: Configure Keycloak
After Keycloak starts successfully, configure the realm, roles, client, and users.

Follow the detailed instructions available in the file [**keycloak.md**](keycloak.md) to setup.

This setup creates:

- Realm
  - modern-distributed-microservices

- Roles
  - ADMIN
  - INSTRUCTOR
  - STUDENT

- Users
  - admin
  - instructor
  - student

- Client
  - gateway-client


<img width="3468" height="1802" alt="image" src="https://github.com/user-attachments/assets/834eb15c-87db-4517-8645-81272b095489" />

### Step 4: Create Databases and Sample Data

Create the required PostgreSQL databases (mentioned in scripts).

Execute the SQL scripts available inside each service.

Examples:

- [user-service/database/user_db.sql](user-service/src/main/resources/database/user_db.sql)
- [course-service/database/course_db.sql](course-service/src/main/resources/database/course_db.sql)
- [enrollment-service/database/enrollment_db.sql](enrollment-service/src/main/resources/database/enrollment_db.sql)
- [certificate-service/database/certificate_db.sql](certificate-service/src/main/resources/database/certificate_db.sql)

The scripts will create:
- Database
- Tables
- Sample Data

<img width="3508" height="1352" alt="image" src="https://github.com/user-attachments/assets/cca73766-a308-4946-bd32-099b4b136587" />

### Step 5: Build the Project

Build all modules from the project root.
```
mvn clean install
```

<img width="3584" height="2238" alt="image" src="https://github.com/user-attachments/assets/ba3688bb-a3e9-47b8-b101-c4983efbca53" />

### Step 6: Start Microservices

Start the services in the following order.

```text
1. discovery-service (Eureka Server)
2. gateway-service (API Gateway)
3. user-service (User Service)
4. course-service (Course Service)
5. enrollment-service (Enrollment Service)
6. certificat-service (Certificate Service)
7. notification-service (Notification Service)
```

### Step 7: Verify Service Registration

Open Eureka Dashboard:

```
http://localhost:8761
```

Verify all services are registered:

```
GATEWAY-SERVICE
USER-SERVICE
COURSE-SERVICE
ENROLLMENT-SERVICE
CERTIFICATE-SERVICE
NOTIFICATION-SERVICE
```

<img width="3584" height="2102" alt="image" src="https://github.com/user-attachments/assets/b6c3696a-6d18-4ece-a818-ffac0eccaa9f" />

### Step 8: Generate JWT Access Token

Generate an access token from Keycloak.

<img width="3584" height="2240" alt="image" src="https://github.com/user-attachments/assets/abff8150-97ea-41d2-8996-649d50897334" />
<br />
This API is available in provided Postman collection if you wish to use from Postman.
<br /><br />
Or CURL command if you wish to use.

```
curl --location 'http://localhost:9090/realms/modern-distributed-microservices/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=gateway-client' \
--data-urlencode 'username=student' \
--data-urlencode 'password=student123'
```

Copy the returned: `access_token`

### Step 9: API Access Rules

The following access rules are configured in the [SecurityConfig](gateway-service/src/main/java/com/example/gateway/config/SecurityConfig.java) class of the `gateway-service`.

| Endpoint Pattern | STUDENT | INSTRUCTOR | ADMIN |
|------------------|---------|------------|--------|
| `/api/v1/users/**` | NO | NO | YES |
| `/api/v1/courses/**` | NO | YES | YES |
| `/api/v1/enrollments/**` | YES | NO | YES |
| `/api/v1/certificates/**` | YES | NO | YES |
| `/actuator/**` | YES | YES | YES |

Use the appropriate JWT token while testing APIs.

### Step 10: Invoke Business APIs

User is created with admin's token
<br /><br />
<img width="3584" height="1382" alt="image" src="https://github.com/user-attachments/assets/f7d48b2f-338d-4cab-b707-aed49526fa88" />

<br /> <br />
**If you notice,** the request was sent from Postman without a CorrelationId header. The gateway-service automatically generated a new CorrelationId and attached it to both the downstream request and the corresponding response. This enables end-to-end request tracking and log correlation across all microservices participating in the request flow.
<br /><br />
<img width="2740" height="1704" alt="image" src="https://github.com/user-attachments/assets/5006b99f-ddd6-4f9f-a7dd-b9a868608aab" />

You can also use CURL commands to call APIs.

Example:
```
curl --location 'http://localhost:8080/api/v1/users' \
--header 'Authorization: Bearer <ACCESS_TOKEN>'
```

### Step 11: Verify Event-Driven Flow

Create an enrollment.

This should trigger:
```
Enrollment Service
↓
enrollment-created-topic (Topic)
↓
Notification Service
```
Verify the application logs for successful event processing.

<img width="2744" height="1054" alt="image" src="https://github.com/user-attachments/assets/913e0dfb-6bae-4af4-897d-0080412f3618" />

<br /><br />

Console log of enrollment-service
<br /><br />
<img width="3362" height="388" alt="image" src="https://github.com/user-attachments/assets/1acfc442-d91b-4f54-81b1-4b1749e0d4a8" />

<br />
Console log of notification-service
<br /><br />
<img width="3360" height="726" alt="image" src="https://github.com/user-attachments/assets/19c8a1f0-053b-4ffe-b5ee-7f66ca0e30fa" />

### Step 12: Verify Kafka Topics

Check on Kafka UI: `http://localhost:8088`

<br />

<img width="3584" height="1462" alt="image" src="https://github.com/user-attachments/assets/d345948d-1edb-49da-9584-01a6f8cfe6ee" />


### Step 13: Verify Distributed Tracing

Execute multiple APIs and observe the logs.

You should see:
- correlationId
- traceId
- spanId

flowing across services.

The same trace should be propagated across all services.

### Step 14: View Traces in Zipkin

Open Zipkin:
```
http://localhost:9411
```

Steps:
1. Open Zipkin UI
2. Click "Run Query"
3. Select a trace
4. View the complete request flow
5. Analyze latency between services

You should be able to trace requests across:
```
Gateway Service
Enrollment Service
Certificate Service
Notification Service
```

<img width="3584" height="2158" alt="image" src="https://github.com/user-attachments/assets/f014c80d-6ab3-4527-8c5e-44323ecff010" />

### Step 15: Verify Fault Tolerance

The platform implements fault tolerance using Resilience4j.

Features
- Circuit Breaker
- Retry
- Fallback

Test Scenario : Stop one of the downstream services. For example `Course Service`

Now try creating a new enrollment.

Expected behavior:

```
Enrollment Service
↓
OpenFeign Call
↓
Course Service Unavailable
↓
Retry Triggered
↓
Circuit Breaker Activated
↓
Fallback Response Returned
```

Verify logs for:
- Retry
- Circuit Breaker
- Fallback

<img width="2738" height="872" alt="image" src="https://github.com/user-attachments/assets/0f50ba77-8bec-4a7c-b43a-dceeb494b5d6" />

### Step 16: Verify Rate Limiting

The API Gateway implements Redis-based rate limiting.

Test Scenario: Call the same API repeatedly within a short period.

Example: `GET /api/v1/users`

Expected behavior:
```
First Requests
↓
Allowed

Subsequent Requests
↓
HTTP 429 Too Many Requests
```

Verify Gateway logs.

<img width="2742" height="708" alt="image" src="https://github.com/user-attachments/assets/28ada36f-829d-4cb7-8bb9-4485a8e8a676" />

### Step 17: Verify Role-Based Access Control (RBAC)

Generate JWT tokens for different users.

Available roles:

```
ADMIN
INSTRUCTOR
STUDENT
```

Test Scenario: Access role-protected endpoints using different tokens.

Expected behavior:
```
Authorized Role
↓
200 OK

Unauthorized Role
↓
403 Forbidden
```

Verify Gateway authorization logs.

User can not be created with student's token (Refer Step9)
<br /><br />
<img width="3584" height="1144" alt="image" src="https://github.com/user-attachments/assets/75f895b0-5d1c-48ff-95aa-5f11c6ca1d5b" />

<br /> <br />
User is created with admin's token
<br /><br />
<img width="3584" height="1382" alt="image" src="https://github.com/user-attachments/assets/f7d48b2f-338d-4cab-b707-aed49526fa88" />

### Step 18: Verify Event-Driven Flow (This time for certification-service)

Create a certificate.

Expected event flow:

```
Certificate Service
↓
CertificateGeneratedEvent
↓
certificate-generated-topic (Topic)
↓
Notification Service
```

Verify:
- Kafka Topics
- Producer Logs
- Consumer Logs

<img width="2740" height="1062" alt="image" src="https://github.com/user-attachments/assets/4b48cab1-b592-44ca-86d7-320a9fe3789b" />
<br /><br />
<img width="3584" height="1460" alt="image" src="https://github.com/user-attachments/assets/bee4da64-d32c-4183-a4a8-0a21cded3369" />
<br /><br />
Console log of notification-service
<br /><br />
<img width="3348" height="812" alt="image" src="https://github.com/user-attachments/assets/00e6e9b1-7804-44ea-8849-6431b525b5c0" />


### Step 19: Access API

You can import the provided Postman collection and invoke APIs.

Or

Swagger UI is also available for each service.

```text
http://localhost:8081/swagger-ui.html
http://localhost:8082/swagger-ui.html
http://localhost:8083/swagger-ui.html
http://localhost:8084/swagger-ui.html
```


# Summary

This project demonstrates practical implementation of:

- Spring Boot Microservices
- Spring Cloud Components
- API Gateway Pattern
- Service Discovery Pattern
- OAuth2 Security
- JWT Authentication
- Keycloak Integration
- Kafka Event-Driven Architecture
- Redis Rate Limiting
- OpenFeign Communication
- Resilience4j Fault Tolerance
- OpenTelemetry Tracing
- Zipkin Monitoring
- Production-Ready Design Patterns


# License

Free software, [Siraj Chaudhary](https://www.linkedin.com/in/sirajchaudhary/)
