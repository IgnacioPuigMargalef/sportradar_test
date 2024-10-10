# Betting Processing System Project

## Exercise Description

This project involves developing a real-time betting processing system. The system must handle different bet states (OPEN, WINNER, LOSER, VOID) and ensure data integrity while processing a high load of requests.

## Implemented Solution

The implemented solution is as follows:

When a bet enters the system, it is placed in a queue. The defined workers are responsible for processing the bets. These workers ensure the integrity of the bets. When an OPEN bet enters the system, it checks if another bet with the same ID is already being processed, and if so, it is flagged for review. If no previous bet exists (which would be a simple control case), the bet is passed to a cache. After that, the worker is released.

If the same bet is processed later but with the WINNER/LOSER state, the system checks if it is in the cache, processes it (calculating profit/loss), and then places it in another queue. This second queue is consumed by two workers whose sole task is to insert the bets into the database. Throughout this process, the number of processed bets is tracked.

Bets that do not follow the normal flow are flagged for review and are sent to the database with a status of `check = YES`.


## Requirements
- **Java 21**: Ensure that JDK 21 is installed.
- **Maven**: Use one of the latest versions of Maven to build and manage dependencies.
- **Docker & Docker Compose**: For running PostgreSQL and Redis containers used in this project.

### Prerequisites

Before running the application, make sure you have the following tools installed:

- Docker
- Java 21
- Maven

### Setup and Installation

1. **Start the Docker Containers**:

   This project uses Docker Compose to manage PostgreSQL and Redis. You don't need to manually configure these databases. Simply run the following command to bring up the containers:

   docker-compose up -d

   This will:
     - Spin up a PostgreSQL instance, where the schema will be initialized via an init.sql script.
     - Spin up a Redis instance to be used as a cache for bets with an OPEN status.

2. **Start the Docker Containers**:

   You can run the tests using Maven, which are already set up to connect to the Dockerized PostgreSQL and Redis services.
   To execute the tests and generate a code coverage report, use the following commands:

    mvn test
    mvn test jacoco:report
  
    This will run the test suite and generate the code coverage report, which is approximately 70%. You can view the coverage report in the target/site/jacoco directory after running these commands.


3. **Building the Project**:

    After running the tests, you can build the project. The project is managed with Maven, so building is as simple as running:

    mvn install

    This command will compile the project and package it as a .jar file located in the target directory.
  

4. **Running the Application**

    Once the project is built, you can run the application in one of two ways:
      - Using the generated .jar file: java -jar target/betting-process-service.jar
      - Using Spring Boot's Maven plugin: mvn spring-boot:run

