# Vacancy Checker
This project is a web application that provides job vacancies for job seekers. The first 5 pages from [arbeitnow](https://www.arbeitnow.com/) are pulled in, and then the software checks for new vacancies once an hour (set separately), and if there are any, they will be added to the database. The project is built with the Spring Boot framework, and the database is managed by JPA.

# Technologies
- Java 17
- Spring Boot
- JPA
- H2 Database
- JUnit test

# Project Structure
The project follows a three-layered structure consisting of the following layers:
- Controller layer: handles the HTTP requests and responses, and calls the Service layer.
- Service layer: contains the business logic of the application.
- Repository layer: provides an interface to communicate with the database.
- 
# Controller Class
## VacancyController
This controller handles HTTP requests related to job vacancies. It has two endpoints:
- /api/jobs: Returns a list of job vacancies sorted by a specified property.
- /api/jobs/location-stats: Returns a map of job vacancies grouped by their location.

# How to Use
- Clone this repository.
- Set up the H2 database and update the configuration in the application.properties file.
- Run the project using the mvn spring-boot:run command.
- Access the API endpoints using the appropriate HTTP requests.