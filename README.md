# ExpenseTracker 💰

> A comprehensive RESTful API for tracking personal and organizational expenses with Spring Boot and PostgreSQL

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-blue.svg)](https://www.postgresql.org/)

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)
- [Security](#security)

## 🎯 Overview

ExpenseTracker is a production-ready RESTful API that simplifies expense management for individuals and organizations. Built with modern Java technologies, it provides a robust backend for tracking expenses, managing categories, and organizing financial data with comprehensive tagging support.

## ✨ Features

### Core Functionality
- 👤 **User Management** - Complete CRUD operations for user accounts and profiles
- 💳 **Expense Tracking** - Record, categorize, and manage expenses with detailed metadata
- 🏷️ **Smart Organization** - Flexible category and multi-tag system for advanced filtering
- 🔍 **Advanced Queries** - Filter expenses by user, category, date range, and tags

### Developer Experience
- 📚 **Interactive API Docs** - Built-in Swagger UI for testing and exploration
- ✅ **Input Validation** - Automatic request validation with Jakarta Bean Validation
- 🛡️ **Error Handling** - Global exception handling with meaningful error responses
- 🏥 **Health Monitoring** - Built-in health check endpoints
- 🔐 **Environment Security** - Secure configuration management with dotenv

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| **Language** | Java 17+ |
| **Framework** | Spring Boot 4.0.5 |
| **Database** | PostgreSQL 12+ |
| **ORM** | Spring Data JPA (Hibernate) |
| **Validation** | Jakarta Bean Validation |
| **Documentation** | Springdoc OpenAPI (Swagger) |
| **Build Tool** | Maven 3.6+ |
| **Utilities** | Lombok, Spring Dotenv |

## 📦 Prerequisites

Ensure you have the following installed:

| Requirement | Version | Purpose |
|------------|---------|---------|
| **JDK** | 17+ | Runtime environment |
| **Maven** | 3.6+ | Build tool (or use wrapper) |
| **PostgreSQL** | 12+ | Database server |
| **Git** | Latest | Version control |

## 🚀 Quick Start

### 1️⃣ Clone the Repository

```bash
git clone <repository-url>
cd ExpenseTracker
```

### 2️⃣ Set Up Database

Create your PostgreSQL database:

```sql
CREATE DATABASE expense_tracker;
```

### 3️⃣ Configure Environment

Create a `.env` file in the project root:

```env
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/expense_tracker
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Server Configuration
SERVER_PORT=8080

# Hibernate Configuration
HIBERNATE_DDL_AUTO=update
SHOW_SQL=true
HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect

# Logging
LOG_LEVEL_APP=DEBUG
LOG_LEVEL_WEB=INFO
```

> 💡 **Tip**: Use `.env.example` as a template for all required variables

### 4️⃣ Build and Run

```bash
# Using Maven
mvn clean install
mvn spring-boot:run

# OR using Maven Wrapper (Windows)
mvnw.cmd clean install
mvnw.cmd spring-boot:run

# OR using Maven Wrapper (Linux/Mac)
./mvnw clean install
./mvnw spring-boot:run
```

### 5️⃣ Verify Installation

Visit these URLs to confirm everything works:
- **API Health**: http://localhost:8080/api/health
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs

---

## ⚙️ Configuration

### Application Properties

The application uses environment variables loaded from a `.env` file for secure configuration management.

**Key Configuration Files:**
- `.env` - Your actual credentials (⚠️ **never commit this**)
- `.env.example` - Template showing required variables (committed to Git)
- `src/main/resources/application.properties` - Spring Boot configuration using env vars

### Environment Variables Reference

| Variable | Description | Default | Example |
|----------|-------------|---------|---------|
| `DB_URL` | Database connection URL | - | `jdbc:postgresql://localhost:5432/expense_tracker` |
| `DB_USERNAME` | PostgreSQL username | - | `postgres` |
| `DB_PASSWORD` | PostgreSQL password | - | `your_secure_password` |
| `SERVER_PORT` | Application server port | `8080` | `8080` |
| `HIBERNATE_DDL_AUTO` | Schema management strategy | `update` | `update`, `create`, `validate`, `none` |
| `SHOW_SQL` | Show SQL queries in logs | `true` | `true`, `false` |
| `HIBERNATE_DIALECT` | Hibernate dialect | - | `org.hibernate.dialect.PostgreSQLDialect` |
| `LOG_LEVEL_APP` | Application log level | `DEBUG` | `DEBUG`, `INFO`, `WARN`, `ERROR` |
| `LOG_LEVEL_WEB` | Spring Web log level | `INFO` | `DEBUG`, `INFO`, `WARN`, `ERROR` |

### Hibernate DDL Modes

Choose the appropriate schema management strategy:

| Mode | Behavior | Use Case |
|------|----------|----------|
| `update` | Updates schema automatically | ✅ **Development** (recommended) |
| `create` | Drops and recreates schema | ⚠️ Testing (data loss!) |
| `create-drop` | Creates on start, drops on shutdown | ⚠️ Integration tests |
| `validate` | Validates schema, no changes | ✅ **Production** (recommended) |
| `none` | Disables DDL handling | Manual migration tools |

### Sample application.properties

```properties
# Server Configuration
server.port=${SERVER_PORT:8080}

# Database Configuration
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=${HIBERNATE_DDL_AUTO:update}
spring.jpa.show-sql=${SHOW_SQL:true}
spring.jpa.properties.hibernate.dialect=${HIBERNATE_DIALECT}
spring.jpa.properties.hibernate.format_sql=true

# Logging Configuration
logging.level.com.ExpenseTracker=${LOG_LEVEL_APP:DEBUG}
logging.level.org.springframework.web=${LOG_LEVEL_WEB:INFO}
```

---

## 🏃 Running the Application

### Development Mode

```bash
# Using Maven
mvn spring-boot:run

# Using Maven Wrapper (Windows)
mvnw.cmd spring-boot:run

# Using Maven Wrapper (Linux/Mac)
./mvnw spring-boot:run
```

### Production Mode

```bash
# Build the JAR
mvn clean package

# Run the JAR
java -jar target/ExpenseTracker-0.0.1-SNAPSHOT.jar
```

The application starts at **http://localhost:8080** (or your configured `SERVER_PORT`).

---

## 📚 API Documentation

Interactive API documentation is available once the application is running:

| Resource | URL | Description |
|----------|-----|-------------|
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Interactive API explorer |
| **OpenAPI Spec (JSON)** | http://localhost:8080/v3/api-docs | OpenAPI 3.0 specification |
| **Health Check** | http://localhost:8080/api/health | Application health status |

> 💡 **Tip**: Use Swagger UI to explore endpoints, view request/response schemas, and test API calls directly from your browser.

---

## 📁 Project Structure

```
ExpenseTracker/
├── 📄 pom.xml                           # Maven configuration
├── 📄 README.md                         # This file
├── 📄 .env                              # Environment variables (⚠️ never commit)
├── 📄 .env.example                      # Environment template
├── 📄 .gitignore                        # Git ignore rules
├── 📄 application.properties.template   # Config template
│
├── src/main/
│   ├── java/com/ExpenseTracker/
│   │   ├── 📱 ExpenseTrackerApplication.java  # Main application class
│   │   │
│   │   ├── controller/                  # 🎮 REST Controllers
│   │   │   ├── ExpenseController.java
│   │   │   ├── ExpenseCategoryController.java
│   │   │   ├── TagController.java
│   │   │   ├── UserController.java
│   │   │   ├── UserProfileController.java
│   │   │   └── HealthController.java
│   │   │
│   │   ├── dto/                         # 📦 Data Transfer Objects
│   │   │   ├── ExpenseRequestDTO.java
│   │   │   ├── ExpenseResponseDTO.java
│   │   │   ├── UserRequestDTO.java
│   │   │   ├── UserResponseDTO.java
│   │   │   └── ...
│   │   │
│   │   ├── entity/                      # 🗄️ JPA Entities
│   │   │   ├── Expense.java
│   │   │   ├── ExpenseCategory.java
│   │   │   ├── Tag.java
│   │   │   ├── User.java
│   │   │   └── UserProfile.java
│   │   │
│   │   ├── exception/                   # ⚠️ Exception Handling
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── DuplicateResourceException.java
│   │   │   └── ErrorResponse.java
│   │   │
│   │   ├── repository/                  # 💾 Data Access Layer
│   │   │   ├── ExpenseRepository.java
│   │   │   ├── ExpenseCategoryRepository.java
│   │   │   ├── TagRepository.java
│   │   │   ├── UserRepository.java
│   │   │   └── UserProfileRepository.java
│   │   │
│   │   └── service/                     # 🔧 Business Logic
│   │       ├── impl/                    # Service implementations
│   │       │   ├── ExpenseServiceImpl.java
│   │       │   ├── ExpenseCategoryServiceImpl.java
│   │       │   ├── TagServiceImpl.java
│   │       │   ├── UserServiceImpl.java
│   │       │   └── UserProfileServiceImpl.java
│   │       ├── ExpenseService.java
│   │       ├── ExpenseCategoryService.java
│   │       ├── TagService.java
│   │       ├── UserService.java
│   │       └── UserProfileService.java
│   │
│   └── resources/
│       ├── application.properties       # Spring Boot configuration
│       ├── static/                      # Static resources
│       └── templates/                   # Template files
│
└── src/test/
    └── java/com/ExpenseTracker/
        └── ExpenseTrackerApplicationTests.java
```

### Architecture Overview

```
┌─────────────┐      ┌─────────────┐      ┌─────────────┐      ┌─────────────┐
│ Controllers │ ───▶ │   Services  │ ───▶ │ Repositories│ ───▶ │  Database   │
│   (REST)    │ ◀─── │  (Business) │ ◀─── │    (JPA)    │ ◀─── │ (PostgreSQL)│
└─────────────┘      └─────────────┘      └─────────────┘      └─────────────┘
       │                    │
       │                    │
       ▼                    ▼
┌─────────────┐      ┌─────────────┐
│    DTOs     │      │  Entities   │
│ (Transfer)  │      │   (Domain)  │
└─────────────┘      └─────────────┘
```

---

## 🔗 API Endpoints

### 🏥 Health & Monitoring

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/health` | Application health check |

### 👤 User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users` | Create a new user |
| `GET` | `/api/users` | Retrieve all users |
| `GET` | `/api/users/{id}` | Retrieve user by ID |
| `PUT` | `/api/users/{id}` | Update existing user |
| `DELETE` | `/api/users/{id}` | Delete user |

### 👥 User Profiles

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/user-profiles` | Create user profile |
| `GET` | `/api/user-profiles` | Retrieve all profiles |
| `GET` | `/api/user-profiles/{id}` | Retrieve profile by ID |
| `PUT` | `/api/user-profiles/{id}` | Update user profile |
| `DELETE` | `/api/user-profiles/{id}` | Delete user profile |

### 📂 Expense Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/expense-categories` | Create new category |
| `GET` | `/api/expense-categories` | Retrieve all categories |
| `GET` | `/api/expense-categories/{id}` | Retrieve category by ID |
| `PUT` | `/api/expense-categories/{id}` | Update category |
| `DELETE` | `/api/expense-categories/{id}` | Delete category |

### 🏷️ Tags

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/tags` | Create new tag |
| `GET` | `/api/tags` | Retrieve all tags |
| `GET` | `/api/tags/{id}` | Retrieve tag by ID |
| `PUT` | `/api/tags/{id}` | Update tag |
| `DELETE` | `/api/tags/{id}` | Delete tag |

### 💳 Expenses

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/expenses` | Create new expense |
| `GET` | `/api/expenses` | Retrieve all expenses |
| `GET` | `/api/expenses/{id}` | Retrieve expense by ID |
| `PUT` | `/api/expenses/{id}` | Update expense |
| `DELETE` | `/api/expenses/{id}` | Delete expense |
| `GET` | `/api/expenses/user/{userId}` | Get expenses by user |
| `GET` | `/api/expenses/category/{categoryId}` | Get expenses by category |
| `POST` | `/api/expenses/{expenseId}/tags/{tagId}` | Add tag to expense |
| `DELETE` | `/api/expenses/{expenseId}/tags/{tagId}` | Remove tag from expense |

> 📖 For detailed request/response schemas and examples, visit the [Swagger UI](http://localhost:8080/swagger-ui.html).

---

## 🧪 Testing

Run the test suite with Maven:

```bash
# Using Maven
mvn test

# Using Maven Wrapper (Windows)
mvnw.cmd test

# Using Maven Wrapper (Linux/Mac)
./mvnw test

# Run tests with coverage report
mvn clean test jacoco:report
```

---

## 🐛 Troubleshooting

### Common Issues

<details>
<summary><strong>❌ Database Connection Failed</strong></summary>

**Symptoms**: `Connection refused` or `Authentication failed`

**Solutions**:
1. Verify PostgreSQL is running:
   ```bash
   # Windows
   Get-Service postgresql*
   
   # Linux/Mac
   sudo systemctl status postgresql
   ```
2. Check database exists:
   ```sql
   \l  -- List all databases in psql
   ```
3. Verify credentials in `.env` file
4. Ensure database URL is correct (host, port, database name)
5. Check PostgreSQL accepts connections on localhost

</details>

<details>
<summary><strong>❌ Port 8080 Already in Use</strong></summary>

**Symptoms**: `Port 8080 is already in use`

**Solutions**:
1. Change port in `.env`:
   ```env
   SERVER_PORT=8081
   ```
2. Or stop the process using port 8080:
   ```bash
   # Windows
   netstat -ano | findstr :8080
   taskkill /PID <PID> /F
   
   # Linux/Mac
   lsof -ti:8080 | xargs kill -9
   ```

</details>

<details>
<summary><strong>❌ Build Failures</strong></summary>

**Symptoms**: Maven build errors

**Solutions**:
1. Verify Java version:
   ```bash
   java -version  # Should be 17+
   ```
2. Clean Maven cache:
   ```bash
   mvn clean
   rm -rf target/  # Or manually delete target folder
   ```
3. Update dependencies:
   ```bash
   mvn clean install -U
   ```
4. Ensure internet connection for dependency download

</details>

<details>
<summary><strong>❌ Environment Variables Not Loaded</strong></summary>

**Symptoms**: Default values used instead of `.env` values

**Solutions**:
1. Ensure `.env` file is in project root directory
2. Verify file is named exactly `.env` (not `.env.txt`)
3. Check for syntax errors in `.env` (no quotes needed for values)
4. Restart application after modifying `.env`

</details>

### Getting Help

If you encounter issues not covered here:
1. Check application logs in the console
2. Enable debug logging: `LOG_LEVEL_APP=DEBUG` in `.env`
3. Review [Spring Boot Documentation](https://spring.io/projects/spring-boot)
4. Check [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

## 🔒 Security

### Environment Configuration

| ⚠️ Critical Security Rules |
|---------------------------|
| ✅ **DO**: Use `.env` for sensitive configuration |
| ✅ **DO**: Commit `.env.example` as a template |
| ✅ **DO**: Add `.env` to `.gitignore` |
| ❌ **DON'T**: Commit `.env` to version control |
| ❌ **DON'T**: Hard-code credentials in source files |

### Production Best Practices

- 🔐 **Secrets Management**: Use platform-specific services (AWS Secrets Manager, Azure Key Vault, etc.)
- 🔑 **Password Policy**: Use strong passwords; rotate regularly
- 🔒 **Database Security**: Enable SSL/TLS for database connections
- 🛡️ **Authentication**: Implement proper authentication and authorization (JWT, OAuth2, etc.)
- 📦 **Dependencies**: Keep all dependencies up to date
- 🚫 **Hibernate DDL**: Use `validate` or `none` in production (never `create` or `update`)
- 📝 **Logging**: Avoid logging sensitive information (passwords, tokens, PII)

### Security Checklist

- [ ] `.env` file is in `.gitignore`
- [ ] No credentials in `application.properties` or source code
- [ ] Using strong database passwords
- [ ] SSL/TLS enabled for production database
- [ ] Authentication/authorization implemented
- [ ] Input validation enabled on all endpoints
- [ ] Dependencies scanned for vulnerabilities
- [ ] Error messages don't leak sensitive information

---

## 📄 License

This project is available for use under standard open source practices.

## 👥 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Support

For questions or issues:
- 📖 Check the [Troubleshooting](#troubleshooting) section
- 💬 Open an issue on GitHub
- 📚 Review the [API Documentation](#api-documentation)

---

**Built with ❤️ using Spring Boot**