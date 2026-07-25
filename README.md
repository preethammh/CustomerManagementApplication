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
  - Axios
  - Modern CSS

## Getting Started

### Prerequisites
Install the following before running the application:

  - Java 21+
  - Node.js 20+
  - npm
  - Git

### Running the Backend

```
cd backend
./mvnw spring-boot:run
```

### The backend will start on:

http://localhost:8080

H2 Console (if enabled):

http://localhost:8080/h2-console

### Running the Frontend
```
cd frontend
npm install
npm run dev
```

The frontend will be available at:

http://localhost:5173

## API Endpoints
### Create Customer
```
POST /api/customers
```
Request Body
```
{
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1995-04-20"
}
```

### Get Customers
```
GET /api/customers
```
Returns all stored customer records.
```
[
  {
    "id":"1234",
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "1995-04-20"
  }
]
```