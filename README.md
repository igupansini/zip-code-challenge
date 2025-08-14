# zip-code-challenge
Code developed for the Stefanini/Santander challenge

## How to Run

### Build the project JAR file:
mvn clean package

### Start the application using Docker Compose:
docker-compose up

The application will be available at http://localhost:8080.

## Architecture
The architecture of this project follows the layered Spring Boot pattern, organized as follows:

1. **REST Layer**: Handles HTTP requests and exposes endpoints.
    - ZipCodeResource.java
    - ZipCodeRequestLogResource.java

2. **Service Layer (Business Logic)**: Contains business logic and orchestrates operations between REST and Repository.
    - ZipCodeService.java
    - ZipCodeRequestLogService.java

3. **Repository Layer (Persistence)**: Handles database communication using JPA.
    - ZipCodeRepository.java
    - ZipCodeRequestLogRepository.java

4. **Domain Layer (Entities)**: Represents data to database tables.
    - ZipCode.java
    - ZipCodeRequestLog.java

5. **Configuration**: Defines project properties and database connection settings.
    - application.properties
    - application-dev.properties

**Data Flow**:
User → REST → Service → Repository → Database
Database → Repository → Service → REST → User

## REST APIs
The available REST APIs in this project are:

- /api/zip-codes

- /api/zip-codes/{id}

- /api/request-logs

- /api/request-logs/{id}

- /api/request-logs/zip/{zip}