# 🐾 Petstore API Test Automation Framework

A professional REST API test automation framework designed for the [Swagger Petstore](https://petstore.swagger.io/) application. This project demonstrates a modern approach to automation in Java, utilizing code generation, fluent assertions, and advanced reporting.

## 🚀 Technologies & Libraries

* **Java 21** - The latest LTS version of the language.
* **Gradle** - Build automation system (custom tasks included).
* **Rest Assured** - HTTP client for API testing.
* **OpenAPI Generator** - Automatic model (POJO) generation from Swagger specification (`swagger.json`).
* **JUnit 5** - Testing engine (supports parameterized tests).
* **AssertJ** - Assertion library (used to build Fluent Assertions).
* **Allure Report** - Detailed test reports (steps, logs, graphs).
* **Lombok** - Boilerplate code reduction (logging).
* **Logback / SLF4J** - Event logging.

## ⚙️ How It Works (Build Automation)

The project features a fully automated lifecycle. You do not need to manually write model classes or download API specifications. Gradle handles this for you in the following order:

1.  **`downloadSwagger`**: Downloads the latest `swagger.json` file from the Petstore server.
2.  **`openApiGenerate`**: Generates Java classes (models like `Pet`, `Category`) based on the downloaded JSON. Files are placed in `build/generated`.
3.  **`compileJava`**: Compiles the generated code and the project code.
4.  **`test`**: Executes the tests.

## 🛠️ Setup & Execution Guide

**Prerequisite:** JDK 21 installed on your system.

### 1. Run all tests
This command will download Swagger, generate code, and run tests:
```bash
./gradlew clean test
```

### 2. Generate and view Allure Report
After running tests, start the report server:
```bash
./gradlew allureServe
```
*(This will automatically open the browser with results).*

### 3. Compilation only (Code Check)
If you want to verify if the project builds (without running tests):
```bash
./gradlew compile
```

## 📂 Project Structure

```text
├── build.gradle            # Main configuration (dependencies, tasks)
├── petstore.json           # (Optional) Local copy of the specification
├── src
│   └── test
│       ├── java
│       │   └── info.gedlek
│       │       ├── api         # API Client (HTTP Logic, RestAssured)
│       │       ├── asserters   # Custom Assertions (Assert Object Pattern)
│       │       └── tests       # Test Classes (JUnit 5)
│       └── resources
│           └── logback-test.xml # Logging configuration
└── build
    └── generated           # Generated classes (Pet.java, etc.) land HERE
```

## 🧩 Design Patterns Used

### 1. API Client Object (`PetApiClient`)
HTTP connection logic (GET, POST, DELETE) is separated from tests. Tests call business methods, e.g., `createPet()`, instead of building requests manually. We also utilize the `@Step` annotation from Allure here.

### 2. Fluent Assertions (`PetAsserter`)
Instead of standard assertions, we use a custom asserter based on AssertJ. This allows writing readable chains of checks:
```java
PetAsserter.assertThat(createdPet)
    .toHaveName("Burek")
    .toHaveStatus(StatusEnum.AVAILABLE);
```

### 3. Model First (Code Generation)
Classes like `Pet`, `Tag`, `Category` are not located in `src/main/java`. They are generated "on the fly" into the `build/generated` directory. This ensures the code is always consistent with the API documentation.
