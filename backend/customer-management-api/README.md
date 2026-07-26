# Customer Management API

This module provides the REST API for the customer management application. It is built with Java 21 and Spring Boot and exposes endpoints for creating and listing customer profiles.

## Features

- Create customer records through a validated REST endpoint
- Retrieve customers with pagination and sorting
- Use Flyway migrations to initialize the database schema
- Store data in an embedded H2 database for local development
- Expose OpenAPI documentation with Swagger UI

## Prerequisites

- Java 21+
- Maven (or use the provided Maven wrapper)
- Git

## Running the API

From the backend module directory:

```bash
./mvnw spring-boot:run
```

The application will start on:

- http://localhost:8080

## API Documentation

Swagger UI is available at:

- http://localhost:8080/swagger-ui/index.html

## Main Endpoints

### Create a customer

```http
POST /api/v1/customer-management/customer
Content-Type: application/json
```

Example body:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1995-04-20"
}
```

### List customers

```http
GET /api/v1/customer-management/customers?page=0&size=10&sortBy=firstName
```

## Testing

Run the test suite with:

```bash
./mvnw test
```

## Project Notes

- The API uses Spring Data JPA and Flyway.
- Database migrations are stored under src/main/resources/db/migration.
- Logging is configured through Log4j2.
