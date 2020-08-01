package com.thesidh.zmovie.web

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.thesidh.zmovie.rest.App
import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.storage.holder.MongoDBHolder
import com.thesidh.zmovie.storage.sample.movieSample
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.*
import java.text.SimpleDateFormat

class MovieTest {

    @Test
    fun `given that a valid movie is supplied, should sucessfully insert a movie and return http status 200`() {
        val movie = movieSample().copy(title = "Teste Zero")
        postMovie(movie, HttpStatus.OK_200)
    }

    @Test
    fun `given that a movie is already saved, if try to save another movie with the same title should fail and return http status CONFLICT_409`() {
        val movie1 = movieSample().copy(title = "ET2")
        val movie2 = movieSample().copy(title = "ET2")
        postMovie(movie1, HttpStatus.OK_200)
        postMovie(movie2, HttpStatus.CONFLICT_409)
        println()
    }

    @Test
    fun `given that a movie with more than 10 actors is supplied, should fail to insert the movie and return http status BAD_REQUEST_400`() {
        val movie = movieSample().copy(
            actors = listOf(
                "Drew Barrymore",
                "Henry Thomas",
                "ET",
                "Actor4",
                "Actor5",
                "Actor6",
                "Actor7",
                "Actor8",
                "Actor9",
                "Actor10",
                "Actor11"
            )
        )
        postMovie(movie, HttpStatus.BAD_REQUEST_400)
    }

    @Test
    fun `given that a rate is supplied, should sucessfully return a list of all movies with that rate and http status 200`() {
        val movie = movieSample().copy(rate = Rate.CENSURADO)
        postMovie(movie, HttpStatus.OK_200)
        getMovies(Rate.CENSURADO, HttpStatus.OK_200)
    }

    private val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .registerModule(JavaTimeModule()).setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
        .setDateFormat(SimpleDateFormat("yyyy-MM-ss"))

    companion object {
        private val mongoHolder = MongoDBHolder.getInstance()

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mongoHolder.start()
            RestAssured.requestSpecification = RequestSpecBuilder()
                .setBaseUri("http://localhost:7000")
                .setBasePath("/")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build()

            App.start()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            mongoHolder.stop()
            App.shutdown()
        }
    }

    fun shutdown() {
        App.shutdown()
    }

    init {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                shutdown()
            }
        })
    }

    private fun postMovie(
        movie: Movie,
        expectedStatus: Int
    ) = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsBytes(movie)).post("/movie").then()
        .statusCode(expectedStatus)
        .extract()
        .jsonPath()

    private fun getMovies(
        rate: Rate,
        expectedStatus: Int
    ) = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsBytes(rate)).get("/movies/$rate").then()
        .statusCode(expectedStatus)
        .extract()
        .jsonPath()


}