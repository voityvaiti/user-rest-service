# User REST service

## Project Structure

- **Project Type:** Maven
- **Java Version:** 17
- **Framework:** Spring Boot 3.2.5

## Additional Notes

- **Packaging:** The application is packaged as a JAR file.
- **Artifact ID:** user-rest-api
- **Group ID:** com.myproject.task
- **Version:** 0.0.1-SNAPSHOT

## Requirements

- **Java Development Kit (JDK):** JDK 17 or later should be installed.
- **Maven:** Maven should be installed for building and managing the project dependencies.
- **Database:** The application requires PostgreSQL to be installed and running. The minimum supported version is
  PostgreSQL 15.3.
- **IDE:** An Integrated Development Environment (IDE) such as IntelliJ IDEA, Eclipse, or NetBeans can be used for
  development.

## Running the Application

1. Clone the project from the repository.
2. Import the project into your preferred IDE.
3. Database configuration details such as URL, username, password, etc., should be provided in
   the `application.properties` file.
4. Build the project using Maven: `mvn clean install`.
5. Run the application: `java -jar target/email-verifier-rest-service-0.0.1-SNAPSHOT.jar`. Or change packaging to `war`
   and deploy it to Tomcat Server.
6. Access the application endpoints using a web browser or API testing tool.

## Endpoints

#### Default API prefix: `/api/v1`

### Get Users By Birth Date Range

- **Method**: GET
- **URL**: `/{api-prefix}/users/birthdate`
- **Description**: Gets users within a specified birth date range.
- **Parameters**:
    - `from` (required). Format: `dd-MM-yyyy`
    - `to` (required). Format: `dd-MM-yyyy`
- **Responses**:
    - `200`:
      ```json
      [
        {
          "id": 0,
          "email": "string",
          "firstName": "string",
          "lastName": "string",
          "birthDate": "2024-04-25",
          "address": "string",
          "telNumber": "string"
        }
      ]
    - `404`:
      ```json
      {
        "timestamp": "YYYY-MM-DD HH:MM:SS",
        "message": "string"
      }

### Create User

- **Method**: POST
- **URL**: `/{api-prefix}/users`
- **Description**: Creates a new user.
- **Request Body**:
  ```json
  {
    "email": "string",         // required
    "firstName": "string",     // required
    "lastName": "string",      // required
    "birthDate": "dd-MM-yyyy", // required
    "address": "string",       // optional
    "telNumber": "string"      // optional
  }
- **Responses**:
    - `200`:
       ```json
       {
         "id": 0,
         "email": "string",
         "firstName": "string",
         "lastName": "string",
         "birthDate": "dd-MM-yyyy",
         "address": "string",
         "telNumber": "string"
       }
    - `404`:
      ```json
      {
        "timestamp": "YYYY-MM-DD HH:MM:SS",
        "message": "string"
      }

### Update User

- **Method**: PUT
- **URL**: `/{api-prefix}/users/{id}`
- **Description**: Updates all user fields.
- **Parameters**:
    - `id` (path, integer, required): ID of the user to be updated.
- **Request Body**:
     ```json 
     {
       "email": "string",         // required
       "firstName": "string",     // required
       "lastName": "string",      // required
       "birthDate": "dd-MM-yyyy", // required
       "address": "string",       // optional
       "telNumber": "string"      // optional
     }
- **Responses**:
    - `200`:
       ```json
       {
         "id": 0,
         "email": "string",
         "firstName": "string",
         "lastName": "string",
         "birthDate": "dd-MM-yyyy",
         "address": "string",
         "telNumber": "string"
       }
    - `404`:
      ```json
      {
        "timestamp": "YYYY-MM-DD HH:MM:SS",
        "message": "string"
      }

### Patch User

- **Method**: PATCH
- **URL**: `/{api-prefix}/users/{id}`
- **Description**: Update fileds that are provided in request body.
- **Parameters**:
    - `id` (path, integer, required): ID of the user to be partially updated.
- **Request Body**:
     ```json 
     {
       "email": "string",         // optional
       "firstName": "string",     // optional
       "lastName": "string",      // optional
       "birthDate": "dd-MM-yyyy", // optional
       "address": "string",       // optional
       "telNumber": "string"      // optional
     }
- **Responses**:
    - `200`:
       ```json
       {
         "id": 0,
         "email": "string",
         "firstName": "string",
         "lastName": "string",
         "birthDate": "dd-MM-yyyy",
         "address": "string",
         "telNumber": "string"
       }
    - `404`:
      ```json
      {
        "timestamp": "YYYY-MM-DD HH:MM:SS",
        "message": "string"
      }

### Delete User

- **Method**: DELETE
- **URL**: `/{api-prefix}/users/{id}`
- **Description**: Delete a user by ID.
- **Parameters**:
    - `id` (path, integer, required): ID of the user to be deleted.
- **Responses**:
    - `200`: OK
    - `404`:
      ```json
      {
        "timestamp": "YYYY-MM-DD HH:MM:SS",
        "message": "string"
      }