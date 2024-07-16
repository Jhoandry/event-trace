# Alert Term Extraction

The **_Alert Term Extraction project_** aims to process simplified Prewave data to extract specific information from text. For this task, there are two REST APIs that return JSON objects accessible via a GET request using a personal API key:

- **testQueryTerm API:** Returns a JSON-encoded list of query terms to extract.

  URL: ` https://services.prewave.ai/adminInterface/api/testQueryTerm`
  

- **testAlerts API:** Returns a sequence of alert objects structured with content arrays.

  URL: `https://services.prewave.ai/adminInterface/api/testAlerts`

### Endpoints and Functionality
The project implements the following endpoints to handle query term extraction and alert matching:

 - **GET** `/events`: Retrieves matches from alerts extraction. Body Response:
   ```json
   [{
        "queryTermId": 101,
        "alertId": "6gbujhu89786",
        "contents": [
            {
                "text": "Wolfgang Lemb, ig metall Germany stands in solidarity with #StrikeForBlackLives",
                "language": "de",
                "matches": 3
            }
        ]
    },
    {
        "queryTermId": 103,
        "alertId": "6gbujhu89786",
        "contents": [
            {
                "text": "Wolfgang Lemb, ig metall Germany stands in solidarity with #StrikeForBlackLives",
                "language": "de",
                "matches": 3
            }
        ]
    }]
   ```
   **queryTermId**: Query Term to which the match belongs.
   **alertId**: Query Term to which the match belongs.
   **contents**: Contains all the text matched, the language and occurrences.


 - **Scheduler for Synchronization**: Will be implemented to execute the alert processing task every two minutes and sync the latest content matches from the APIs.

### Required Technologies
- Java 21
- Maven 3.8.1 or higher

### Project Structure
The application follows the layers architecture and its structure is organized into the following packages:

  - Presentation Layer

    `Controllers`: Handle incoming HTTP requests and send HTTP responses.

  
  - Service Layer

    `Services`: Implement business logic and coordinate between the presentation layer and the persistence layer.


  - Persistence Layer

    `Repositories`: Manage data access and storage, performing CRUD operations. 


### Installation

1. **Clone the repository:**

    ```bash
    git clonehttps://github.com/Jhoandry/event-trace.git
    cd event-trace
    ```

2. **Build the project with Maven:**

    ```bash
    mvn clean install
    ```

### Execution

To run the application locally, use the following Maven command:

```bash
mvn spring-boot:run
```

The application will be available at [http://localhost:8080](http://localhost:8080)

### Tests

To run the tests, use the following command:

```bash
mvn clean test
```




  