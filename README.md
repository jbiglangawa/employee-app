# Employee App
This is an app created for the Backend-test to qualify for LegalMatch.
It contains basic logic to list, create, update, and delete Employees
into an internal tool. 

Note: The repository name is set to Employee App to avoid leaking the code
on github.

## Technologies used
Here are the technologies used in this project
- Java 17
- Maven
- Spring Boot 3.3.2
  - Spring MVC
  - Spring JPA
  - Hibernate
  - GraphQL for Spring
  - Spring Security
  - Thymeleaf
- MySQL 8.0
- Flyway 10.7.1
- Pico CSS 2.0.6
- JQuery 3.7.1
- Fontawesome 5.0
- JS-Cookies 3.0.1
- io.jsonwebtoken 0.11.5

Flyway helps migrate the scripts under resources/migration and will run the 
scripts in sequential order. This will happen firsthand during server spin-up.

Decided to go with JWT Authentication instead of Basic Authentication to make
it better. Of course, 0Auth 2.0 is still the best bet for better authentication
but JWT helped with a faster spin-up but still a secure system. The bearer token 
is saved in the browser in the cookies with JS-Cookies.

The Frontend logic is in Vanilla Javascript ES6 approach. With Pico CSS in the 
picture, it was quicker to spin up the UI.

And lastly, as per instructions, the API is set to use via GraphQL. GraphIQL isn't
enabled so you'd probably need Postman to access the GraphQL Endpoints. 

## Getting Started
I've setup docker to spin up the setup process quickly. To run it in your local docker
environment, clone this repository via: 
```shell
git clone https://github.com/jbiglangawa/employee-app.git
``` 

After that, spin-up docker by going to the workspace directory and running: 
```shell
docker compose up -d
```

The MySQL server is configured to run along with the API Server, so there are no additional
configurations required there. But of course, this only works because it's an exam. When
running on production, we shouldn't run the database on the same container, and we should
set it up on cloud. Maybe Azure SQL, Azure Cosmos DB, Amazon DynamoDB and/or Amazon RDBMS
could work.

In the event you want to spin it up on your local, the configuration can be changed in the `application.yml` file.
Just change the properties below:
```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/legalmatch
    username: mysql_user
    password: test_pw
```

To start testing the GraphQL API, here's my Postman workspace to help you get started quickly:
[Link to Postman Workspace](https://www.postman.com/swingspringer/workspace/employee-api/collection/669deb8aac392377cf1d5fc6?action=share&creator=13546457&active-environment=13546457-ecd48dde-7769-4aa4-803e-71de402ed516).
Be sure to change the domain when you test. After that, just run the Get Token to
save the Bearer Token in the Environment Variables and you can start sending requests. 
The IP Address of the server will be provided in the email, be sure to use that in the domain.


## Setting up SonarQube
SonarQube is a tool Developers can use to gain insights on what parts of the project to fix and improve.
However setting it up isn't quick and easy, as it's a self-hosted tool meant to be run on the CI/CD pipeline. 
So I've setup a docker compose under `/sonarqube/docker-compose.yml`. Run that using `docker compose up -d` 
and access SonarQube from `http://localhost:9001`.

Once the server is up, [follow the instructions here](https://medium.com/@denis.verkhovsky/sonarqube-with-docker-compose-complete-tutorial-2aaa8d0771d4)
to get an API token from SonarQube. No need to create the docker-compose file and add the dependencies on the pom file,
it's added already. Simply follow the steps till you get an API token:

```shell
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=SonarQubeDockerDemo \
  -Dsonar.host.url=http://localhost:9001 \
  -Dsonar.login=sqp_9fcc11d4b0d851fe988eecb43535307b0d5a65ed
```

After running, here's the results report: ![sonarcube results](/assets/sonarqube-result.png)
