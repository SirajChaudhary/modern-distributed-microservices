# Keycloak Setup Guide

## Overview

Keycloak is an open-source Identity and Access Management (IAM) solution used to provide:

* Authentication
* Authorization
* Single Sign-On (SSO)
* OAuth2 and OpenID Connect support
* JWT Token generation and validation

In this project, Keycloak is used to secure APIs exposed through the API Gateway using JWT-based authentication and Role-Based Access Control (RBAC).

---

# Access Keycloak

Open Keycloak Admin Console:

```text
http://localhost:9090
```

Login using the administrator credentials (admin/admin) configured during Keycloak startup.

---

# Create Realm

A Realm is an isolated security domain that contains users, roles, and clients.

Navigate to:

```text
Keycloak Admin Console
→ Create Realm
```

Create:

```text
Realm Name: modern-distributed-microservices
```

Click:

```text
Create
```

---

# Create Roles

Navigate to:

```text
Realm
→ Roles
```

Create the following roles:

```text
STUDENT
INSTRUCTOR
ADMIN
```

---

# Create Gateway Client

Navigate to:

```text
Clients
→ Create Client
```

Enter:

```text
Client ID: gateway-client
```

Click:

```text
Next
```

Configure:

```text
Client Authentication: OFF
Authorization: OFF
```

Enable:

```text
Direct Access Grants: ON
```

```text
Standard Flow: ON
```

Click:

```text
Save
```

---

# Create Student User

Navigate to:

```text
Users
→ Create User
```

Enter:

```text
Username: student
Email: student@example.com
First Name: Student
Last Name: User
```

Enable:

```text
Enabled: ON
Email Verified: ON
```

Click:

```text
Create
```

---

# Set Password

Navigate to:

```text
Credentials
```

Enter:

```text
Password: student123
```

Set:

```text
Temporary: OFF
```

Click:

```text
Save
```

---

# Assign Role

Navigate to:

```text
Role Mapping
```

Assign role (here do 'Filter by realm roles' and select):

```text
STUDENT
```

to the user.

---

# Create Instructor User

Create another user:

```text
Username: instructor
Email: instructor@example.com
First Name: Instructor
Last Name: User
```

Password:

```text
instructor123
```

Assign Role:

```text
INSTRUCTOR
```

---

# Create Admin User

Create another user:

```text
Username: admin
Email: admin@example.com
First Name: Admin
Last Name: User
```

Password:

```text
admin123
```

Assign Role:

```text
ADMIN
```

---

# Configure API Gateway

Add the following configuration:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:9090/realms/modern-distributed-microservices
```

This enables JWT validation for all incoming requests.

---

# Generate Access Token

Execute:

```bash
curl --location 'http://localhost:9090/realms/modern-distributed-microservices/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=gateway-client' \
--data-urlencode 'username=student' \
--data-urlencode 'password=student123'
```

Successful response:

```json
{
  "access_token": "eyJ...",
  "refresh_token": "eyJ...",
  "expires_in": 300
}
```

Copy the access token.

---

# Call Protected APIs

Example:

```bash
curl --location 'http://localhost:8080/api/v1/users' \
--header 'Authorization: Bearer <ACCESS_TOKEN>'
```

The API Gateway validates the token before forwarding the request to downstream microservices.

---

# JWT Role Mapping

Example JWT Payload:

```json
{
  "realm_access": {
    "roles": [
      "STUDENT"
    ]
  }
}
```

The Gateway converts Keycloak roles into Spring Security authorities.

---

# Security Flow

```text
Client
   |
   | Access Token
   v
API Gateway
   |
   | JWT Validation
   v
Keycloak

   |
   | Authorized Request
   v

User Service
Course Service
Enrollment Service
Certificate Service
```
