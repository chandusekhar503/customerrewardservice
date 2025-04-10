# customerrewardservice
This is a RESTful Spring Boot application that provides APIs to fetch customer reward points based on their transaction history. 
The service calculates rewards based on predefined rules, such as transaction amount, date, and customer activity.


## Prerequisites:
 - JDK 17
 - Maven
 - Spring Boot 3.x

## Project Structure:
```graphql
customerrewardservice/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── retailer/
│   │   │           └── customerrewardservice/
│   │   │               ├── controller/         # REST API endpoints (HTTP request handlers)
│   │   │               ├── service/            # Business logic layer
│   │   │               ├── dao/                # Mock customer and transaction data access layer
│   │   │               ├── data/               # Mock data for customer and transactions.
│   │   │               ├── dto/                # All the dtos, request and response 
│   │   │               └── CustomerrewardserviceApplication.java    # Main class with @SpringBootApplication
│   │   │
│   │   └── resources/
│   │       ├── application.properties          # Application configuration
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── retailer/
│                   └── customerrewardservice/
│                       ├── unit/            # Tests for business logic
│                       └── integration/        # Integration tests
│
├── .gitignore
├── README.md                                   # Project documentation
├── pom.xml                                     # Maven dependencies and build config
└── mvnw / mvnw.cmd                             # Maven wrapper scripts
```




## Installation

### Clone Repository
```bash
  git clone https://github.com/chandusekhar503/customerrewardservice.git
  cd customerrewardservice
```

### Install dependencies
```bash
 mvn clean install
```

### Run the Application
```bash
mvn spring-boot::run
```

### Package into jar file and run
```bash
mvn clean package
java -jar target/customerrewardservice-0.0.1-SNAPSHOT.jar
```

## API Endpoints:

| Method | Endpoint              | Description         |
|--------|-----------------------|---------------------|
| POST   | /api/v1/rewards/fetch | Fetch Reward Points |


### curl command:
#### Input:
```
curl --location 'http://localhost:8080/api/v1/rewards/fetch' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "c11",
    "fromDate": "01-01-2025",
    "toDate": "31-03-2025"
}'
```

#### success output with HttpStatus 200:
```
{
    "customerId": "c1",
    "customerName": "chandra",
    "rewards": [
        {
            "month": 1,
            "monthName": "JANUARY",
            "rewardPoints": 160
        },
        {
            "month": 2,
            "monthName": "FEBRUARY",
            "rewardPoints": 20
        },
        {
            "month": 3,
            "monthName": "MARCH",
            "rewardPoints": 0
        }
    ]
}
```

#### When there is no transactions found for customer with HttpStatus 200:
```
{
    "customerId": "c1",
    "customerName": "chandra",
    "rewards": null
}
```

#### Validation Error with HttpStatus 400:
```
customerId is null:

{
    "errorMessage": "Validation error",
    "customerId": "customerId should not be blank.",
    "errorCode": "4001"
}

fromDate is null:

{
    "fromDate": "fromDate should not be empty.",
    "errorMessage": "Validation error",
    "errorCode": "4001"
}

toDate is null:

{
    "toDate": "toDate should not be empty.",
    "errorMessage": "Validation error",
    "errorCode": "4001"
}
```

#### Business Error with HttpStatus 500:
```
Customer Not Found:

{
    "errorMessage": "Customer Not Found",
    "errorCode": "1001"
}
```

## Conclusion:
Thank you for checking out this project! We hope you find it useful for your needs. If you have any questions or feedback, feel free to reach out or open an issue on GitHub.
