# Customer Management UI

This frontend module provides the React and TypeScript interface for the customer management application. It communicates with the Spring Boot backend through a Vite proxy.

## Features

- Add new customers through a form
- Display customers in a paginated list
- Use typed API models for frontend requests and responses
- Run locally with Vite on port 3000

## Prerequisites

- Node.js 20+
- npm

## Installation

From the frontend module directory:

```bash
npm install
```

## Running the App

```bash
npm run dev
```

The development server will be available at:

- http://localhost:3000

## Build

To create a production build:

```bash
npm run build
```

## Linting

```bash
npm run lint
```

## Notes

- API calls are proxied to http://localhost:8080 through Vite.
- The UI uses the backend endpoints under /api/v1/customer-management.

