# spring-security-jwt

This repository contains an example Spring project implementing authentication and authorization using Spring Security with JWT. The project provides a set of APIs for user registration, login, and role-based access control.

## Features

- **User Registration**: Allows new users to sign up by providing necessary details.
- **User Login**: Authenticates users and provides a JWT token for subsequent requests.
- **Role-Based Access Control**: Access control for specific endpoints based on user roles (`MASTER` and `USER`).

## APIs

The following endpoints are available:

- **User Signup**  
  - **Endpoint**: `/signup`
  - **Method**: `POST`
  - **Description**: Registers a new user.
  - **Request Body**: `SignupRequestDto` (required)

- **User Login**  
  - **Endpoint**: `/sign`
  - **Method**: `POST`
  - **Description**: Authenticates a user and returns a JWT token.
  - **Request Body**: `LoginRequestDto` (required)

- **Admin Only Access**  
  - **Endpoint**: `/admin`
  - **Method**: `GET`
  - **Description**: Grants access to users with the `MASTER` role.
  - **Authorization**: Requires `MASTER` role.

- **User Only Access**  
  - **Endpoint**: `/user`
  - **Method**: `GET`
  - **Description**: Grants access to users with the `USER` role.
  - **Authorization**: Requires `USER` role.

## Setup Instructions

1. **Development Environment**  
   To run the project in a development environment, execute the following:
   ```bash
   docker-compose -f docker-compose.dev.yml up
   ```

2. **Production Deployment**  
   To deploy in production, update the `.env` file with appropriate production configurations, then run:
   ```bash
   docker-compose -f docker-compose.prod.yml up
   ```

## API Documentation

The project includes Swagger API documentation, accessible at:
```
/docs
```

## Dependencies

- Spring Boot
- Spring Security
- JWT
- Swagger (for API documentation)
