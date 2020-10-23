# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

- This project is to manage User Management using services like get and put.
- This project is based on Document first API approach, where swagger doc is added first and all models, request and response model is auto generated using open-api plugin. That's why please execute `mvn clean install` after downloading project to generate models automatically.
- Jacoco Test report has implemented to get visibility on test coverage.
- Minimum 90% test coverage has maintained in this repo using Jacoco, otherwise build will fail.
- Junit, Mockito is used to write unit tests and Spring MVC Test are written to add test coverage on api.

### How to run

`mvn test` runs all unit tests

`mvn clean package` to generate jar file after running all unit tests

`mvn clean install` to generate jar file after running all unit tests

`mvn verify` runs all unit tests

### Run application locally using docker
1. Make sure docker daemon process is running
2. Run maven command `mvn clean install`
3. Then run `docker build -t springio/gs-spring-boot-docker .` to create image
4. Then run command `docker run -e "SPRING_PROFILES_ACTIVE=dev" -p 7001:7001 -t springio/gs-spring-boot-docker` to run application locally on 9800 port
5. Use `curl` to test health endpoint: `curl 'http://localhost:7001/actuator/health`

### Api Doc
1. There is swagger doc inside resource foler `api_doc.yml` for all API endpoints and explanation with required fields.
2. There is also postman json added inside resource folder to execute API's.

### Health check endpoint
Request: 
curl 'http://localhost:7001/actuator/health'

Response:
```
{
    "status": "UP"
}
```
