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

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Built With

* [Kotlin](https://kotlinlang.org/) - The programming language that does more with less code
* [Javalin](http://https://javalin.io/) - A lightweight web framework for Java and Kotlin
* [MongoDB](https://www.mongodb.com/) - NoSQL document based database
* [Koin](https://insert-koin.io/) - Dependency injection for Kotlin
* [Gradle](https://gradle.org/) - More than just a building toolkit

## Author

* **Sidharta Rezende** 



