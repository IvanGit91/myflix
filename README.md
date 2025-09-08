# MyFlix Backend

A comprehensive Spring Boot application for managing media content, characters, and powers in a media streaming platform. This backend provides RESTful APIs with JWT authentication, PostgreSQL database integration, and a robust architecture designed for scalability.

## Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Building the Application](#building-the-application)
- [Running the Application](#running-the-application)
- [Docker Deployment](#docker-deployment)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [TODO](#todo)
- [License](#license)

## Overview

MyFlix is a backend service designed to power a media streaming platform. It manages various entities including media content (movies, TV shows), characters, powers, missions, and user authentication. The application follows clean architecture principles with clear separation of concerns across controllers, services, repositories, and entities.

## Key Features

### 🔐 Authentication & Security
- **JWT-based Authentication**: Secure token-based authentication system
- **Spring Security Integration**: Role-based access control
- **BCrypt Password Encoding**: Secure password hashing
- **CORS Support**: Cross-origin resource sharing enabled

### 📊 Data Management
- **PostgreSQL Database**: Robust relational database support
- **JPA/Hibernate**: Object-relational mapping with automatic DDL generation
- **Custom Repository Pattern**: Enhanced repository pattern with base implementations
- **Entity Relationships**: Complex entity relationships for media, characters, and powers

### 🎬 Media Features
- **Media Management**: CRUD operations for movies and TV shows
- **Character System**: Character profiles with associated powers and attributes
- **Power System**: Supernatural abilities and character traits
- **Mission & Objectives**: Quest-like features for enhanced user engagement

### 🌐 API Features
- **RESTful Architecture**: Clean, intuitive API endpoints
- **JSON Response Format**: Standardized JSON responses
- **Field Filtering**: Dynamic field selection for optimized responses
- **Enum Exposure**: Dynamic enum values exposed via REST endpoints

### 🔧 Developer Experience
- **Hot Reload**: Spring Boot DevTools for development
- **Comprehensive Testing**: JUnit 5, Mockito, and Hamcrest integration
- **Lombok Integration**: Reduced boilerplate code
- **Multi-language Support**: i18n capabilities with locale resolution

## Technology Stack

- **Framework**: Spring Boot 3.4.6
- **Language**: Java 17
- **Database**: PostgreSQL 42.7.7
- **Security**: Spring Security with JWT (JJWT 0.13.0)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven 3
- **Testing**: JUnit 5, Mockito, Hamcrest
- **Utilities**: Lombok, Apache Commons Lang, Jackson

## Prerequisites

Before running this application, ensure you have:

- **Java 17** or higher installed
- **Maven 3.6+** for dependency management
- **PostgreSQL 12+** database server
- **Docker** (optional, for containerized deployment)

## Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd backend
   ```

2. **Database Setup**:
   - Install PostgreSQL
   - Create a database named `myflix`
   - Create a user with appropriate privileges

3. **Configuration**:
   - Copy `src/main/resources/application.example` to `application.yml`
   - Update database credentials and JWT secrets (see [Configuration](#configuration))

## Configuration

### Database Configuration

Create `src/main/resources/application.yml` based on `application.example`:

```yaml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/myflix
    username: your_username
    password: your_password
  jpa:
    show-sql: true
    generate-ddl: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: create
    database: postgresql

jwtSecret: your-256-bit-secret-key-here
jwtExpiration: 86400
```

### Application Profiles

The application supports multiple profiles:

- **Development** (`dev`): Local development with detailed logging
- **Integration Test** (`integration_test`): Testing environment
- **Docker** (`docker`): Container deployment

### Server Configuration

Default server configuration:
- **Port**: 7070
- **Context Path**: `/api`
- **Property Naming**: Lower camel case

## Building the Application

### Using Maven Wrapper (Recommended)

```bash
# Windows
./mvnw clean install

# Linux/Mac
./mvnw clean install
```

### Using System Maven

```bash
mvn clean install
```

### Build with Tests

```bash
# Run all tests
./mvnw clean test

# Skip tests
./mvnw clean install -DskipTests
```

## Running the Application

### Development Mode

```bash
./mvnw spring-boot:run
```

### Using JAR File

```bash
# Build first
./mvnw clean package

# Run the JAR
java -jar target/myflix-0.0.1-SNAPSHOT.jar
```

### With Specific Profile

```bash
java -jar target/myflix-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

The application will start on `http://localhost:7070/api`

## Docker Deployment

### Build Docker Image

```bash
# Build the application
./mvnw clean package

# Build Docker image
docker build -t myflix-backend .
```

### Run with Docker

```bash
docker run -p 7070:7070 --name myflix-app myflix-backend
```

### Docker Compose (Recommended)

Create a `docker-compose.yml`:

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "7070:7070"
    depends_on:
      - postgres
    environment:
      - SPRING_PROFILES_ACTIVE=docker
  
  postgres:
    image: postgres:13
    environment:
      POSTGRES_DB: myflix
      POSTGRES_USER: myflix
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
```

## API Documentation

### Base URL
```
http://localhost:7070/api
```

### Authentication Endpoints
- `POST /auth/login` - User login
- `POST /auth/register` - User registration

### Media Endpoints
- `GET /media` - Get all media
- `GET /media/{id}` - Get media by ID
- `GET /media/title/{title}` - Get media by title
- `POST /media` - Create new media
- `PUT /media/{id}` - Update media
- `DELETE /media/{id}` - Delete media

### Character Endpoints
- `GET /characters` - Get all characters
- `GET /characters/{id}` - Get character by ID
- `POST /characters` - Create new character
- `PUT /characters/{id}` - Update character
- `DELETE /characters/{id}` - Delete character

### Power Endpoints
- `GET /powers` - Get all powers
- `GET /powers/{id}` - Get power by ID
- `POST /powers` - Create new power
- `PUT /powers/{id}` - Update power
- `DELETE /powers/{id}` - Delete power

### Utility Endpoints
- `GET /enums` - Get all exposed enums
- `GET /enums/{enumType}` - Get specific enum values

## Testing

The project includes comprehensive test suites:

### Run All Tests
```bash
./mvnw test
```

### Test Categories
- **Unit Tests**: `SampleApplicationTests.java`
- **Mockito Tests**: `SampleMockitoApplicationTests.java`
- **Hamcrest Tests**: `SampleHamcrestApplicationTests.java`
- **Test Suite**: `TestSuiteApplicationTests.java`

### Coverage Report
```bash
./mvnw test jacoco:report
```

## Project Structure

```
src/
├── main/
│   ├── java/me/personal/myflix/
│   │   ├── config/              # Configuration classes
│   │   ├── controller/          # REST controllers
│   │   │   ├── base/           # Base controller implementations
│   │   │   └── flix/           # Media-specific controllers
│   │   ├── dto/                # Data Transfer Objects
│   │   ├── entity/             # JPA entities
│   │   │   ├── base/           # Base entity classes
│   │   │   └── flix/           # Media domain entities
│   │   ├── enums/              # Application enumerations
│   │   ├── exception/          # Custom exceptions
│   │   ├── payload/            # Request/Response payloads
│   │   ├── repository/         # Data access layer
│   │   │   ├── base/           # Base repository pattern
│   │   │   └── flix/           # Domain-specific repositories
│   │   ├── security/           # Security configuration & JWT
│   │   ├── service/            # Business logic layer
│   │   │   ├── base/           # Base service implementations
│   │   │   └── flix/           # Domain services
│   │   └── utility/            # Utility classes and helpers
│   └── resources/
│       ├── messages/           # i18n message files
│       ├── application.yml     # Main configuration
│       ├── application-dev.yml # Development configuration
│       └── application.example # Configuration template
└── test/                       # Test classes
```

## TODO

The following features and improvements are planned for future development:

### 🎯 User Experience Enhancements

#### User Catalog Management
- **Watched Media Tracking**: Implement user catalog system to track seen/watched media
- **Liked Media System**: Add like/dislike functionality for media content
- **Favorites Management**: Create favorite media collections for users
- **Watchlist Feature**: Allow users to create and manage watchlists
- **Watch History**: Track viewing history with timestamps and progress
- **Rating System**: Implement user ratings and reviews for media content
- **Recommendation Engine**: Build recommendation system based on user preferences

### 🗄️ Database & Data Management

#### Improved DB Relationships
- **Enhanced Entity Constraints**: Add more comprehensive database constraints and validations
- **Referential Integrity**: Strengthen foreign key relationships and cascade operations
- **Database Indexes**: Optimize query performance with strategic indexing
- **Data Validation**: Implement comprehensive input validation at entity level
- **Audit Trail**: Add creation/modification timestamps and user tracking
- **Soft Delete**: Implement soft delete functionality for critical entities
- **Database Migration Scripts**: Create versioned migration scripts for schema changes

### 🔐 Security Improvements

#### Enhanced Security Measures
- **Role-Based Access Control (RBAC)**: Implement granular permission system
- **API Rate Limiting**: Add rate limiting to prevent abuse
- **Request Validation**: Strengthen input sanitization and validation
- **HTTPS Enforcement**: Ensure all communication is encrypted
- **Security Headers**: Implement security headers (HSTS, CSP, X-Frame-Options)
- **OAuth2 Integration**: Add OAuth2 support for third-party authentication
- **Password Policies**: Implement strong password requirements and policies
- **Session Management**: Improve session handling and timeout mechanisms
- **Audit Logging**: Add comprehensive security audit logging
- **Vulnerability Scanning**: Integrate automated security vulnerability checks

### ⚡ Event-Driven Architecture

#### Apache Kafka Integration
- **Event Streaming**: Implement Kafka for real-time event processing
- **Microservices Communication**: Use Kafka for inter-service communication
- **External Event Management**: Handle external system events through Kafka topics
- **Data Synchronization**: Implement eventual consistency across services
- **Event Sourcing**: Consider event sourcing pattern for critical business events
- **Dead Letter Queues**: Implement error handling with DLQ patterns
- **Event Schema Registry**: Maintain event schema versioning and compatibility

### 📊 Performance & Monitoring

#### Performance Optimizations
- **Caching Layer**: Implement Redis for caching frequently accessed data
- **Database Query Optimization**: Optimize complex queries and add proper indexing
- **Connection Pooling**: Fine-tune database connection pool settings
- **Lazy Loading**: Implement lazy loading for entity relationships
- **Pagination Improvements**: Enhance pagination for large datasets

#### Monitoring & Observability
- **Application Metrics**: Integrate Micrometer for application metrics
- **Health Checks**: Implement comprehensive health check endpoints
- **Distributed Tracing**: Add tracing with Zipkin or Jaeger
- **Centralized Logging**: Implement ELK stack for log aggregation
- **Performance Monitoring**: Add APM tools for performance insights

### 🧪 Testing & Quality

#### Enhanced Testing
- **Integration Tests**: Expand integration test coverage
- **Contract Testing**: Implement consumer-driven contract testing
- **Load Testing**: Add performance and load testing suites
- **Security Testing**: Implement automated security testing
- **Test Data Management**: Improve test data setup and teardown
- **End-to-End Testing**: Implement comprehensive E2E test scenarios
- **API Testing**: Add dedicated API testing with REST Assured or similar
- **Database Testing**: Implement database-specific tests with Testcontainers
- **Mutation Testing**: Add mutation testing to verify test quality
- **Smoke Testing**: Create smoke test suites for quick validation
- **Regression Testing**: Build automated regression test suites
- **Cross-browser Testing**: Add browser compatibility testing for web clients
- **Mobile Testing**: Implement mobile-specific API testing scenarios

### 🚀 DevOps & Deployment

#### CI/CD Improvements
- **Pipeline Automation**: Enhance CI/CD pipelines with more thorough checks
- **Environment Management**: Improve environment-specific configurations
- **Blue-Green Deployment**: Implement zero-downtime deployment strategies
- **Container Orchestration**: Add Kubernetes deployment configurations
- **Infrastructure as Code**: Implement IaC with Terraform or CloudFormation

### 🔌 API Enhancements

#### Advanced API Features
- **API Versioning**: Implement comprehensive API versioning strategy
- **GraphQL Support**: Consider GraphQL endpoint for flexible data fetching
- **API Documentation**: Generate interactive API documentation with Swagger/OpenAPI
- **WebSocket Support**: Add real-time communication capabilities
- **Batch Operations**: Implement batch API endpoints for bulk operations

### 🌍 Internationalization (i18n) & Localization

#### Enhanced Multi-language Support
- **Extended Locale Support**: Add support for more languages and regions
- **Dynamic Language Switching**: Allow runtime language switching without restart
- **Content Localization**: Localize media content titles, descriptions, and metadata
- **Date/Time Formatting**: Implement locale-specific date and time formatting
- **Currency Localization**: Add multi-currency support with proper formatting
- **Number Formatting**: Implement locale-specific number and decimal formatting
- **Right-to-Left (RTL) Support**: Add support for RTL languages like Arabic, Hebrew
- **Pluralization Rules**: Implement proper pluralization for different languages
- **Message Interpolation**: Enhanced message formatting with parameter substitution
- **Locale-specific Validation**: Implement locale-aware input validation
- **Time Zone Support**: Add comprehensive time zone handling
- **Character Encoding**: Ensure proper UTF-8 handling across all components
- **Translation Management**: Implement translation workflow and tools
- **Fallback Mechanisms**: Create graceful fallbacks for missing translations

### 📱 Mobile & Frontend Support

#### Client Integration
- **Mobile API Optimization**: Optimize APIs for mobile client consumption
- **Offline Support**: Design APIs to support offline-first mobile apps
- **Push Notifications**: Implement push notification system
- **Content Delivery**: Integrate CDN for media content delivery

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Standards
- Follow Java coding conventions
- Use Lombok annotations appropriately
- Write comprehensive tests for new features
- Update documentation as needed

## License

This project is licensed under the MIT License - see the [LICENSE](#mit-license) section below for details.

---

## MIT License

```
MIT License

Copyright (c) 2024 MyFlix Project

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

**Made with ❤️ for the developer community**
