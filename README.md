# 🐾 Petstore API Test Automation Framework

A professional REST API test automation framework designed for the [Swagger Petstore](https://petstore.swagger.io/) application. This project demonstrates a modern approach to automation in Java, utilizing code generation, fluent assertions, meta-annotations, and advanced reporting.

## 🚀 Technologies & Libraries

* **Java 21** - The latest LTS version of the language.
* **Gradle 8.5** - Build automation system (wrapper configured for compatibility).
* **Rest Assured** - HTTP client for API testing.
* **OpenAPI Generator** - Automatic model (POJO) generation from Swagger specification (`swagger.json`).
* **JUnit 5** - Testing engine (supports parameterized tests, custom extensions).
* **AssertJ** - Assertion library (used to build Fluent Assertions).
* **Datafaker** - Generation of random, realistic test data.
* **Allure Report** - Detailed test reports (steps, logs, graphs).
* **Lombok** - Boilerplate code reduction.

## ⚙️ How It Works (Build Automation)

The project features a fully automated lifecycle. You do not need to manually write model classes or download API specifications. Gradle handles this for you in the following order:

1.  **`downloadSwagger`**: Downloads the latest `swagger.json` from the Petstore server.
2.  **`openApiGenerate`**: Generates Java classes (models like `Pet`, `Category`) into `build/generated`.
3.  **`compileJava`**: Compiles the generated code and the project source code.
4.  **`test`**: Executes the tests using JUnit 5 Platform.

## 🛠️ Setup & Execution Guide

**Prerequisite:** JDK 21 installed on your system.

### 1. Run all tests (Regression)
This command will download Swagger, generate code, and run **all** tests (Positive + Negative).
```bash
./gradlew clean test
```

### 2. Run specific test groups
Thanks to custom Meta-Annotations, you can filter tests easily:

* **Run Happy Path tests only:**
    ```bash
    ./gradlew clean test -DincludeTags="positive"
    ```
* **Run Error Handling tests only:**
    ```bash
    ./gradlew clean test -DincludeTags="negative"
    ```

### 3. Generate and view Allure Report
After running tests (even if they fail), generate the visual report:
```bash
./gradlew allureServe
```

## 📂 Project Structure

```text
├── build.gradle            # Main configuration (dependencies, tasks)
├── gradle/wrapper          # Gradle Wrapper (ensures version 8.5)
├── src
│   └── test
│       ├── java
│       │   └── info.gedlek
│       │       ├── api         # API Client (HTTP Logic, RestAssured)
│       │       ├── asserters   # Custom Fluent Assertions (PetAsserter)
│       │       ├── tests       # Test Classes & BaseTest
│       │       └── utils       # Utilities
│       │           ├── TestDataGenerator.java  # Datafaker logic
│       │           └── annotations             # Custom Meta-Annotations (@Positive, @Negative)
│       └── resources
│           └── logback-test.xml # Logging configuration
└── build
    └── generated           # Generated classes (Pet.java, etc.) land HERE
```

## 🧩 Design Patterns & Concepts

### 1. Meta-Annotations (Clean Code)
Instead of using raw `@Tag("regression")` strings, we use custom annotations to group tests logically and avoid typos.
* **`@Positive`**: Adds tags: `positive`, `smoke`, `regression`.
* **`@Negative`**: Adds tags: `negative`, `validation`, `regression`.

### 2. API Client Object (`PetApiClient`)
HTTP connection logic (GET, POST, DELETE) is separated from tests. Tests call business methods, e.g., `createPet()`, instead of building requests manually.

### 3. Test Data Factory (`TestDataGenerator`)
We do not hardcode data in tests. The `TestDataGenerator` class uses **Datafaker** to create random, realistic objects (Pets, Categories) on demand.
```java
// Example usage in test
var pet = TestDataGenerator.generateDefaultPet();
```

### 4. Fluent Assertions (`PetAsserter`)
Custom assertions based on AssertJ allow writing readable chains of checks:
```java
PetAsserter.assertThat(createdPet)
    .toHaveName(pet.getName())
    .toHaveStatus(StatusEnum.AVAILABLE);
```

### 5. Base Test Class (`BaseTest`)
Uses `Lifecycle.PER_CLASS` to manage shared configuration. It initializes the API Client and Faker once, providing them to all inheriting test classes.

### 6. Model First (Code Generation)
Classes like `Pet`, `Tag`, `Category` are generated "on the fly" into the `build/generated` directory using OpenAPI Generator. This ensures the code is always consistent with the live API documentation.