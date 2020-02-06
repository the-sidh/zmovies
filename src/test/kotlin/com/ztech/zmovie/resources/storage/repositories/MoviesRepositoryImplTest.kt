package com.ztech.zmovie.resources.storage.repositories

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.domain.exceptions.DatabaseInsertionException
import com.ztech.zmovie.domain.exceptions.DuplicateMovieTitleException
import com.ztech.zmovie.resources.storage.extension.MongoDBExtension
import com.ztech.zmovie.resources.storage.mongodb.MoviesRepositoryImpl
import com.ztech.zmovie.resources.storage.mongodb.entities.MovieDocument
import com.ztech.zmovie.resources.storage.sample.movieSample
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import java.text.SimpleDateFormat
import java.time.LocalDate

@ExtendWith(MongoDBExtension::class)
class MoviesRepositoryImplTest(private val mongoDatabase: MongoDatabase) {
    private val collection: MongoCollection<MovieDocument> =
        mongoDatabase.getCollection(
            "zmovie",
            MovieDocument::class.java
        )

    @BeforeEach
    fun setUp() {
        collection.drop()
    }

    @Test
    fun `given that a movie has already been persisted, other movie with the same title should result in a execption`() {
        val repository = MoviesRepositoryImpl(mongoDatabase)
        val movie1 = movieSample()
        val movie2 = movieSample()
        repository.insertMovie(movie1)
        assertThrows<DuplicateMovieTitleException> { repository.insertMovie(movie2) }

    }

    @Test
    fun `given a valid movie, should persist it`() {
        val repository = MoviesRepositoryImpl(mongoDatabase)
        val movie = movieSample()
        val wasInserted = repository.insertMovie(movie)

        val inserted = collection.find().first()
        Assertions.assertAll(
            Executable { Assertions.assertTrue(wasInserted) },
            Executable { Assertions.assertEquals(movie.title, inserted?.id) },
            Executable { Assertions.assertEquals(movie.director, inserted?.director) },
            Executable { Assertions.assertEquals(movie.releaseDate, inserted?.releaseDate) },
            Executable { Assertions.assertEquals(movie.actors, inserted?.actors) },
            Executable { Assertions.assertEquals(movie.rate, inserted?.rate) }
        )
    }

    @Test
    fun `given an error when trying to persist a valid movie, throw an exception`() {
        val mockMongoDatabase = mockk<MongoDatabase>()
        val mockCollection = mockk<MongoCollection<MovieDocument>>()
        every { mockMongoDatabase.getCollection(any(), MovieDocument::class.java) } returns mockCollection
        every { mockCollection.insertOne(any()) } throws MongoException("")
        val repository = MoviesRepositoryImpl(mockMongoDatabase)
        val movie = movieSample()
        assertThrows<DatabaseInsertionException> { repository.insertMovie(movie) }
    }

    @Test
    fun `given a rate, should return all movies with the given rate`() {
        val repository = MoviesRepositoryImpl(mongoDatabase)
        val movie = movieSample()
        repository.insertMovie(movie)

        val movie2 = Movie(
            title = "Star Wars - a new hope",
            director = "George Lucas",
            releaseDate = SimpleDateFormat("yyyy-MM-dd").parse("1977-1-30"),
            actors = listOf("Harrison Ford", "Mark Hamill", "Carrie Fischer"),
            rate = Rate.SEM_CENSURA
        )
        repository.insertMovie(movie2)

        val movies = repository.retrieveByRate(Rate.SEM_CENSURA)
        Assertions.assertAll(
            Executable { Assertions.assertEquals(movies.size, 2) }
        )
    }
}
