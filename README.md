# Rest Assured Portfolio

## Overview
This repository contains a collection of API automation tests using **Rest Assured**. It serves as a portfolio demonstrating best practices for API testing, including request validation, response assertions, and reporting.

## Technologies Used
- **Java** (Primary language)
- **Rest Assured** (API testing framework)
- **JUnit/TestNG** (Test framework)
- **Maven** (Build tool & dependency management)
- **Allure** (Test reporting)
- **Jackson/Gson** (JSON parsing)

## Installation & Setup

### Prerequisites
Ensure you have the following installed:
- **Java JDK 8+**
- **Maven**
- **Git**

### Clone Repository
```sh
git clone https://github.com/OleksandrTimofieiev/rest-assured-portfolio.git
cd rest-assured-portfolio
```

### Install Dependencies
```sh
mvn clean install
```

## Running Tests
To execute all test cases, run:
```sh
mvn test
```
If using **TestNG**, specify the suite file:
```sh
mvn test -DsuiteXmlFile=testng.xml
```

## Project Structure
```
rest-assured-portfolio/
│── src/test/java/
│   ├── tests/         # API test cases
│   ├── utils/         # Utility classes (e.g., request builders, response handlers)
│   ├── config/        # Configuration files
│── pom.xml            # Project dependencies
│── README.md          # Project documentation
```

## Reports & Logs
To generate and view **Allure Reports**, use:
```sh
mvn clean test allure:report
mvn allure:serve
```

