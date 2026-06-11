# Gateway Service

The Gateway Service acts as the single entry point to the microservices ecosystem.

## Responsibilities

- Request routing
- Authentication
- Authorization
- Correlation ID generation
- Request logging
- Response logging
- Rate limiting
- Global error handling
- Header propagation

## Why Do We Need an API Gateway?

In a distributed microservices architecture, clients should communicate with a single endpoint instead of calling individual microservices directly.

The Gateway centralizes cross-cutting concerns and routes requests to the appropriate downstream services.

### Example

Client calls:

```text
http://localhost:8080/api/users
```

instead of:

```text
http://localhost:8081/api/users
http://localhost:8082/api/courses
http://localhost:8083/api/enrollments
```

The Gateway determines which service should handle the request and forwards it accordingly.

## Benefits

- Centralized security
- Centralized routing
- Reduced client complexity
- Consistent cross-cutting concerns
- Improved maintainability
- Better observability and monitoring

## Service Discovery Integration

The Gateway uses Eureka Service Discovery to locate downstream services dynamically.

Example:

```text
USER-SERVICE
COURSE-SERVICE
ENROLLMENT-SERVICE
```

instead of hardcoded URLs:

```text
http://localhost:8081
http://localhost:8082
http://localhost:8083
```

## Future Responsibilities

As the project evolves, the Gateway will additionally handle:

- JWT validation using Keycloak
- Role-Based Access Control (RBAC)
- Correlation ID generation
- Request/response logging
- Redis-based rate limiting
- Global exception handling
- Trace and span propagation

## Technology

- Spring Cloud Gateway
- Spring Cloud Netflix Eureka Client

## Default URL

```text
http://localhost:8080
```

All client requests should enter the microservices ecosystem through the Gateway.