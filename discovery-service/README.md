# Discovery Service

The Discovery Service acts as the central service registry for the microservices ecosystem.

## Responsibilities

- Service registration
- Service discovery
- Registry management

## Why Do We Need a Discovery Service?

In a distributed microservices architecture, services should not communicate using hardcoded hostnames or URLs.

Instead, services register themselves with Eureka during startup and discover other services dynamically using their logical service names.

### Example

Gateway Service calls:

```text
USER-SERVICE
```

Enrollment Service calls:

```text
COURSE-SERVICE
```

instead of using hardcoded endpoints such as:

```text
http://localhost:8081
http://localhost:8082
```

## Benefits

- Loose coupling between services
- Dynamic service discovery
- Easier scaling and deployment
- Improved maintainability
- No hardcoded service URLs

## Technology

- Spring Cloud Netflix Eureka Server

## Default URL

```text
http://localhost:8761
```

The Eureka Dashboard can be used to view all registered microservices and their current status.