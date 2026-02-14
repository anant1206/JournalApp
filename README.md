# Journal Application

A secure, RESTful journal management application built with Spring Boot 3.5.3 and MongoDB. This application enables users to create, manage, and maintain their personal journal entries with role-based access control and administrative capabilities.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Security](#security)
- [Building & Running](#building--running)
- [Testing](#testing)
- [License](#license)

## Features

- **User Management**: Register and manage user accounts with secure password handling
- **Journal Entry Management**: Create, read, update, and delete personal journal entries
- **Role-Based Access Control**: Support for user and admin roles with different permission levels
- **Motivational Quotes**: Automatic quote generation for new users via external API
- **Application Caching**: Redis-backed caching for improved performance
- **Security**: Spring Security integration with authentication and authorization
- **API Documentation**: Swagger/OpenAPI support for interactive API exploration
- **Input Validation**: Hibernate Validator for robust data validation
- **Development Tools**: Spring DevTools for rapid development and testing

## Technology Stack

- **Framework**: Spring Boot 3.5.3
- **Language**: Java 17
- **Database**: MongoDB
- **Build Tool**: Maven
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Security**: Spring Security
- **ORM/Mapping**: Lombok
- **Testing**: JUnit, Spring Security Test
- **Validation**: Hibernate Validator
- **Caching**: Spring Data Redis (via AppCacheConfig)

## System Requirements

- Java 17 or higher
- Maven 3.6 or higher
- MongoDB 4.0 or higher
- Windows, macOS, or Linux

## Installation

### Prerequisites

1. Install Java 17 JDK
2. Install Maven
3. Install and start MongoDB

### Clone and Setup

```bash
# Clone the repository
git clone <repository-url>

# Navigate to the project directory
cd Journal

# Install dependencies
mvn clean install
```

## Configuration

### Application Profiles

The application uses Spring profiles for environment-specific configurations:

- **dev** (default): Development environment with debug logging
- **prd**: Production environment with optimized settings

### Configuration Files

- `src/main/resources/application.yml` - Main configuration
- `src/main/resources/application-dev.yml` - Development profile
- `src/main/resources/application-prd.yml` - Production profile
- `src/main/resources/application.properties` - Additional properties

### MongoDB Configuration

MongoDB connection details should be configured in the appropriate profile YAML file:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/journal
```

## API Endpoints

### Public Endpoints (No Authentication Required)

#### Health Check
- `GET /public/health-check` - Verify application status

#### User Registration
- `POST /public/create-user` - Register a new user account
  - Request body: `{ "userName": "string", "password": "string" }`
  - Returns a welcome message with a motivational quote

### Authenticated Endpoints (User Role)

#### Journal Management
- `GET /journal` - Retrieve all journal entries for the authenticated user
- `GET /journal/id/{myID}` - Retrieve a specific journal entry by ID
- `POST /journal` - Create a new journal entry
  - Request body: `{ "name": "string", "content": "string" }`
- `PUT /journal/id/{myID}` - Update an existing journal entry

#### User Management
- `GET /user` - Retrieve all users
- `PUT /user/username` - Update current user's credentials
  - Request body: `{ "userName": "string", "password": "string" }`
- `DELETE /user/id/{myID}` - Delete user account

### Administrative Endpoints (Admin Role)

#### Admin Operations
- `GET /admin/all-users` - Retrieve all users in the system
- `POST /admin/create` - Create a new admin user
  - Request body: `{ "userName": "string", "password": "string", "roles": ["ROLE_ADMIN"] }`
- `GET /admin/refresh-app-cache` - Refresh application cache
- `POST /admin/add-app-cache` - Add or update cache configuration

## Project Structure

```
src/
├── main/
│   ├── java/com/spring/Journal/
│   │   ├── JournalApplication.java          # Application entry point
│   │   ├── Config/
│   │   │   ├── AppCacheConfig.java         # Cache configuration
│   │   │   ├── MongoConfig.java            # MongoDB configuration
│   │   │   └── SpringSecurity.java         # Security configuration
│   │   ├── Controller/
│   │   │   ├── AdminController.java        # Admin endpoints
│   │   │   ├── JournalController.java      # Journal entry endpoints
│   │   │   ├── PublicController.java       # Public/registration endpoints
│   │   │   ├── UserController.java         # User management endpoints
│   │   │   └── Repository/                 # Data access objects
│   │   ├── Entity/
│   │   │   ├── AppCache.java              # Cache configuration entity
│   │   │   ├── JournalEntry.java          # Journal entry entity
│   │   │   ├── Quotes.java                # Quote entity
│   │   │   └── User.java                  # User entity
│   │   └── Service/
│   │       ├── JournalEntryService.java   # Journal business logic
│   │       ├── QuotesService.java         # Quote retrieval logic
│   │       ├── UserDetailsServiceImpl.java # Custom user details service
│   │       └── UserService.java           # User business logic
│   └── resources/
│       ├── application.yml                # Main configuration
│       ├── application-dev.yml            # Development configuration
│       ├── application-prd.yml            # Production configuration
│       └── application.properties         # Legacy properties
└── test/
    └── java/com/spring/Journal/
        ├── JournalApplication.java        # Test application
        └── Service/
            ├── JournalEntryServiceTest.java      # Journal service tests
            ├── UserDetailsServiceImplTests.java  # User details service tests
            └── UserServiceTests.java             # User service tests
```

## Security

### Authentication & Authorization

- **Spring Security** is integrated for authentication and authorization
- **Role-Based Access Control (RBAC)** restricts access based on user roles:
  - `ROLE_USER`: Standard user permissions
  - `ROLE_ADMIN`: Administrative permissions
- User credentials (username and password) are validated against stored data in MongoDB
- The `SecurityContextHolder` is used throughout the application to access the authenticated user's information

### Password Security

- Passwords should be encoded before storage (configure in `SpringSecurity.java`)
- Leverage Spring Security's `PasswordEncoder` for secure password handling

### Best Practices

- Use HTTPS in production
- Implement rate limiting for authentication endpoints
- Regularly update dependencies for security patches
- Use environment variables for sensitive configuration

## Building & Running

### Build the Application

```bash
mvn clean package
```

### Run the Application

**Using Maven:**
```bash
mvn spring-boot:run
```

**Using Java (after building JAR):**
```bash
java -jar target/Journal-0.0.1-SNAPSHOT.jar
```

**Specify a Profile:**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prd"
```

### Development with Hot Reload

Spring DevTools is included for automatic restart on code changes:

```bash
mvn spring-boot:run
```

## Testing

### Run All Tests

```bash
mvn test
```

### Test Coverage

The project includes unit tests for:
- Journal Entry Service (`JournalEntryServiceTest.java`)
- User Service (`UserServiceTests.java`)
- User Details Service (`UserDetailsServiceImplTests.java`)

### Test Results

Test reports are generated in: `target/surefire-reports/`

## API Documentation

### Swagger UI

Once the application is running, access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

The OpenAPI specification is available at:

```
http://localhost:8080/v3/api-docs
```

## Development Notes

- **Lombok**: Used for reducing boilerplate code (annotations: `@Data`, `@NoArgsConstructor`)
- **MongoDB BSON ObjectId**: Used as primary key for all entities
- **RestTemplate**: Configured in `JournalApplication.java` for making HTTP requests (used for quotes API)
- **DBRef**: Used in the `User` entity to maintain relationships with `JournalEntry` documents

## Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB service is running
- Verify connection URI in configuration file
- Check network connectivity to MongoDB server

### Build Failures
- Ensure Java 17 is installed: `java -version`
- Clear Maven cache: `mvn clean`
- Verify Maven installation: `mvn -version`

### Port Already in Use
- Change the server port in configuration: `server.port=8081`
- Or stop the process using the current port

## Future Enhancements

- [ ] Add JWT token-based authentication
- [ ] Implement entry search and filtering
- [ ] Add tag/category support for journal entries
- [ ] Export entries to PDF
- [ ] Email notifications for user activities
- [ ] Enhanced user profile management
- [ ] Rate limiting and security improvements

## Contributing

1. Create a feature branch (`git checkout -b feature/amazing-feature`)
2. Commit your changes (`git commit -m 'Add amazing feature'`)
3. Push to the branch (`git push origin feature/amazing-feature`)
4. Open a Pull Request

## Support

For issues, questions, or suggestions, please open an issue in the repository.

---

**Version**: 0.0.1-SNAPSHOT  
**Last Updated**: February 2026
