# Rule Engine Application

This is a Spring Boot-based Rule Engine application that uses MongoDB for storing data. The Rule Engine evaluates user eligibility based on provided rules and supports operations like rule creation, combination, and evaluation.

## Features
- **Create Rules**: Add new rules to the rule engine.
- **Combine Rules**: Combine multiple rules logically.
- **Evaluate Rules**: Evaluate a user's eligibility based on the combined rules.

## Technologies Used
- **Java 17**
- **Spring Boot**
- **MongoDB**
- **Maven 3.2**


Before you can run the application, ensure you have the following installed:

- **Java 17**
- **Maven 3.2**
- **MongoDB** (running on `localhost` with port `27017`)

## Configuration

The application connects to a MongoDB instance running on localhost using the following configuration:

**In application properties:-**

spring.application.name=ruleEngine

spring.data.mongodb.host=localhost

spring.data.mongodb.port=27017

spring.data.mongodb.database=ruleEnginedb

**Running the Application**

Clone the repository:**git clone https://github.com/sanjay0606/ruleEngine.git**

**Navigate to the project directory:** cd ruleEngine

**Build the project using Maven:** mvn clean install

**Run the application:** mvn spring-boot:run

The application will start and be accessible on **http://localhost:8080.**

**API Endpoints**
Here are the available API endpoints:

**BASE-URL: localhost:8080/api/rules**

**1. Create Rule**
This API creates a new rule based on the given string representation.

**URL:** /create

**Method:** POST

**Request Body:**

{
  **"ruleString"**: "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)"
}


Response: Returns the rule ID and the generated AST (Abstract Syntax Tree).


**2. Combine Rules**
This API allows combining two rules using logical operators.

**URL:** /combine

**Method:** POST

**Request Body:**

{
    **"combineoperator"**:"OR",
  **"rules"**: [
    "((age > 30 AND department = 'Sales'))",
    "((age < 25 AND department = 'Marketing'))",
    "((income > 50000 AND spend > 200000))"
  ]
}


Response: Returns the combinedString and node.


**3. Evaluate Rule**
This API evaluates a rule against a given set of user attributes.

**URL**: /evaluate

**Method**: POST

**Request Body:**

{
  **"node"**: {
    "type": "operator",
    "value": "AND",
    "left": {
      "type": "operator",
      "value": "OR",
      "left": {
        "type": "operator",
        "value": "AND",
        "left": {"type": "operand", "value": "age > 30"},
        "right": {"type": "operand", "value": "department = 'Sales'"}
      },
      "right": {
        "type": "operator",
        "value": "AND",
        "left": {"type": "operand", "value": "age < 25"},
        "right": {"type": "operand", "value": "department = 'Marketing'"}
      }
    },
    "right": {
      "type": "operator",
      "value": "OR",
      "left": {"type": "operand", "value": "income > 50000"},
      "right": {"type": "operand", "value": "spend > 200000"}
    }
  },
  **"userAttributes"**: {
    "age": 35,
    "department": "Sales",
    "income": 0,
    "spend": 0
  }
}


Response: Returns the evaluation result (true or false).


**Dependencies**

The project relies on the following **dependencies:**
```xml

<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-mongodb</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
</dependencies>

