## How to run the project and tests
To run the project , execute the below command from the terminal inside the project folder: <br/>
`mvn spring-boot:run` <br/>
That will up docker compose along with th spring boot app

To generate coverage report, run the below in the terminal: <br/>
`mvn clean test` <br/>
That will run the tests and will generate coverage report using jacoco and will store it in: `/target/site/jacoco/index.html`

## Project endpoints
The project has three endpoints protected with basic authentication using `user:123`. <br/>
There is a file: `AppCollection.postman_collection` which is a postman collection that can be imported <br/>

