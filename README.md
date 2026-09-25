# Student Support & Ticket Management System

A Spring Boot based Student Support and Ticket Management System developed as an interview assignment.

## Features

- Student, Staff and Admin roles
- Create and manage support tickets
- Ticket assignment to staff
- Ticket status management
- Priority management
- Ticket comments
- Ticket history/audit trail
- SLA due date and SLA breach tracking
- Search and filter tickets
- REST APIs
- MySQL database
- Role-based authentication and authorization

## Technology Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- Thymeleaf
- Bootstrap
- MySQL
- Maven
- REST API

## Ticket Lifecycle

OPEN → ASSIGNED → IN_PROGRESS → PENDING → RESOLVED → CLOSED

## Project Architecture

Controller → Service → Repository → MySQL

## How to Run

1. Create a MySQL database named `student_support`.
2. Configure database credentials in `application.properties`.
3. Run the Spring Boot application.
4. Open `http://localhost:8080/login`.

## Demo Users

| Role | Email | Password |
|---|---|---|
| Student | student@test.com | 123456 |
| Staff | staff@test.com | 123456 |
| Admin | admin@test.com | 123456 |

## REST API

Main API:

`/api/tickets`

Dashboard API:

`/api/dashboard`

Comments:

`/api/tickets/{id}/comments`

History:

`/api/tickets/{id}/history`


