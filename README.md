# zmovies
Ztech backend challenge


#Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing purposes. 

## Running

Use gradle to raise the docker container with MongoDB and run the application

```
gradle composeUp run
```

# API endpoints

These endpoints allow you to save and retrieve movie information.
There is a [a Postman collection](https://github.com/the-sidh/zmovies/blob/master/postman/ZTech.postman_collection.json) supplied with the code that has sample requests for convenience.

## GET
`Retrieve movies by rate` [/movies/:rate]<br/>
### Request

`GET /movies/SEM_CENSURA`

    curl -i -H 'Accept: application/json' http://localhost:7000/movies/SEM_CENSURA

### Response

    HTTP/1.1 200 OK
    Date: Sat, 08 Feb 2020 16:37:25 GMT
    Server: Javalin
    Content-Type: application/json
    Content-Length: 138

    [{"title":"ET","releaseDate":390960000000,"rate":"SEM_CENSURA","director":"Steven Spielberg","actors":["Drew Barrymore","Henry Thomas"]}]n
## POST
`Save a new movie` [/movie] <br/>
### Request

`POST /thing/`

    curl -X POST \
     http://localhost:7000/movie \
     -H 'Accept: */*' \
     -H 'Accept-Encoding: gzip, deflate' \
     -H 'Cache-Control: no-cache' \
     -H 'Connection: keep-alive' \
     -H 'Content-Length: 190' \
     -H 'Content-Type: application/json' \
     -H 'Host: localhost:7000' \
     -H 'Postman-Token: eaf509f3-4802-43b0-82a2-6a171e200a76,5b491353-6532-4e61-9020-f19226132ca3' \
     -H 'User-Agent: PostmanRuntime/7.19.0' \
     -H 'cache-control: no-cache' \
     -d '{
       "actors": [
           "Drew Barrymore",
           "Henry Thomas"
       ],
       "director": "Steven Spielberg",
       "rate": "SEM_CENSURA",
       "releaseDate": "1982-05-23",
       "title": "ET"
        }

### Response

     {
        "title": "ET",
        "releaseDate": 390960000000,
        "rate": "SEM_CENSURA",
        "director": "Steven Spielberg",
        "actors": [
          "Drew Barrymore",
          "Henry Thomas"
          ]
       }
___

# Running the tests

The code was done relying heavily on TDD, so we have a comprehensive code coverage.
You can run the tests using gradle
```
gradle test
```
Automatic end-to-end testing is supplied by component testing, that evaluates the business rules supplied on the challenge. You can run the components tests also by using gradle
```
gradle componentTest
```

## Integration tests

Integration tests can be achieved by using the postman collection supplied with the code.
![testResults](https://github.com/the-sidh/zmovies/blob/master/testsuccess.png)
# On the joice of the web framework

I chose [Javalin](http://https://javalin.io/), a lightweight web framework that I have been using for one and a half year. 

The learning curve is low and the amount of boiler plate code tends to be amoung the lower for java or kotlin web frameworks. 
It's style resembles Express.

It runs over a jetty server.

# Built With

* [Kotlin](https://kotlinlang.org/) - The programming language that does more with less code
* [Javalin](http://https://javalin.io/) - A lightweight web framework for Java and Kotlin
* [MongoDB](https://www.mongodb.com/) - NoSQL document based database
* [Koin](https://insert-koin.io/) - Dependency injection for Kotlin
* [Gradle](https://gradle.org/) - More than just a building toolkit

# Author

* **Sidharta Rezende** 



