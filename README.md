# Customer Management Application
A full-stack customer management application built as part of a Senior+ Full Stack Technical Test.

## Overview
This project provides a simple application for creating and viewing customer records. It consists of a Spring Boot backend exposing REST APIs and a React frontend for interacting with the application.

The repository is organized as a monorepo containing both frontend and backend applications.

## Project Structure
CustomerManagementApplication/
├── backend/          # Spring Boot REST API
├── frontend/         # React + TypeScript application
├── AI_USAGE.md       # Documentation of AI-assisted development
└── README.md

## Features
  - Create customer records
  - View all customer records
  - Input validation
  - In-memory database for persistence
  - Responsive web interface
  - RESTful API
  - Automated tests

## Technology Stack

### Backend
  - Java 21
  - Spring Boot 4.x
  - Spring Data JPA
  - H2 In-Memory Database
  - Maven

### Frontend
  - React 19
  - TypeScript
  - tailwind CSS

## Getting Started

### Prerequisites
Install the following before running the application:

- Java 21+
- Node.js 20+
- npm
- Git

### Running the Backend

From the repository root:

```bash
cd backend/customer-management-api
./mvnw spring-boot:run
```

The backend will start on:

- http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html

### Running the Frontend

From the repository root:

```bash
cd frontend/customer-management-ui
npm install
npm run dev
```

The frontend will be available at:

- http://localhost:3000

## API Endpoints

### Create Customer

```http
POST /api/v1/customer-management/customer
Content-Type: application/json
```

Request body:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1995-04-20"
}
```

### Get Customers

```http
GET /api/v1/customer-management/customers?page=0&size=10&sortBy=firstName
```

Returns a paginated list of customer records.

## Documentation

- Backend README: [backend/customer-management-api/README.md](backend/customer-management-api/README.md)
- Frontend README: [frontend/customer-management-ui/README.md](frontend/customer-management-ui/README.md)
