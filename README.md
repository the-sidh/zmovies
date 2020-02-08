# zmovies
Ztech backend challenge


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing purposes. 

### Running

Use gradle to raise the docker container with MongoDB and run the application

```
gradle composeUp run
```

## Running the tests

The code was done relying heavily on TDD, so we have a comprehensive code coverage.
You can run the tests using gradle
```
gradle test
```
Automatic end-to-end testing is supplied by component testing, that evaluates the business rules supplied on the challenge. You can run the components tests also by using gradle
```
gradle componentTest
```

### Integration tests

Integration tests can be achieved by using the postman collection supplied with the code.
![testResults](https://github.com/the-sidh/zmovies/blob/master/testsuccess.png)
### On the joice of the web framework

I chose [Javalin](http://https://javalin.io/), a lightweight web framework that I have been using for one and a half year. 

The learning curve is low and the amount of boiler plate code tends to be amoung the lower for java or kotlin web frameworks. 
It's style resembles Express.

It runs over a jetty server.

## Built With

* [Kotlin](https://kotlinlang.org/) - The programming language that does more with less code
* [Javalin](http://https://javalin.io/) - A lightweight web framework for Java and Kotlin
* [MongoDB](https://www.mongodb.com/) - NoSQL document based database
* [Koin](https://insert-koin.io/) - Dependency injection for Kotlin
* [Gradle](https://gradle.org/) - More than just a building toolkit

## Author

* **Sidharta Rezende** 



