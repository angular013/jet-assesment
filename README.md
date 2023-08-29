# Employee Management Application

## Getting Started

### Prerequisites
Java 11 or higher  
Docker (for running postgres database and Kafka/Zookeeper)

### Running the Application
1) Clone the repository from GitHub:
```
git clone https://github.com/your-username/employee-management.git
cd employee-management
```
Import the project in IDE (eg IntelliJ), build and run

2) CD into the root project directory and run the docker-compose.yml file to pull images and run the container
```
docker-compose up -d
```

To delete the container, run :

```
docker-compose down
```

Make sure the ports used in docker-compose file are not already used in your system, which can cause issues.

### Swagger Documentation
The API endpoints are documented using Swagger. Access the Swagger UI by visiting :  
  http://localhost:8080/swagger-ui.html

You may require usernamd and password to enter Swagger UI due to authentication on routes  
  **username :**  testuser  
  **password :**  testpassword  


### Postman API access
If using something like Postman, you will need to define the base64 encoded username:password in the authorization header. These are the headers that will need to be set in Postman :
```
Content-Type:application/json
Accept:application/json
Authorization:Basic dGVzdHVzZXI6dGVzdHBhc3N3b3Jk
```

### API Endpoints
The application exposes the following REST API endpoints:
```
GET /employees: Get a list of all employees.
GET /employees/{uuid}: Get a specific employee by UUID.
POST /employees: Create a new employee.
PUT /employees/{uuid}: Update an existing employee.
DELETE /employees/{uuid}: Delete an employee.
```  

### Sample API request data :

```
{
    "email": "jane2.smith@example.com",
    "firstName": "Jane",
    "lastName": "Test",
    "birthday": "1985-05-28",
    "hobbies": ["trekking", "swimming"]
}
```

### Testing
The application includes both unit and integration tests. Run the tests using:  
```
./mvnw test
```
