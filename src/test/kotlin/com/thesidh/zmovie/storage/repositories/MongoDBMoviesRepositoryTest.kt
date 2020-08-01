package com.thesidh.zmovie.storage.repositories

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.domain.exceptions.DatabaseInsertionException
import com.thesidh.zmovie.domain.exceptions.DuplicateMovieTitleException
import com.thesidh.zmovie.storage.extension.MongoDBExtension
import com.thesidh.zmovie.storage.sample.movieSample
import com.thesidh.zmovie.storage.mongodb.MongoDBMoviesRepository
import com.thesidh.zmovie.storage.mongodb.entities.MovieDocument
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import java.text.SimpleDateFormat

@ExtendWith(MongoDBExtension::class)
class MongoDBMoviesRepositoryTest(private val mongoDatabase: MongoDatabase) {
    private val collection: MongoCollection<MovieDocument> =
        mongoDatabase.getCollection(
            "zmovie",
            MovieDocument::class.java
        )

    @Test
    fun `given that a movie has already been persisted, other movie with the same title should result in a execption`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie1 = movieSample()
        val movie2 = movieSample()
        repository.insertMovie(movie1)
        assertThrows<DuplicateMovieTitleException> { repository.insertMovie(movie2) }

    }

    @Test
    fun `given a valid movie, should persist it`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
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
        val repository = MongoDBMoviesRepository(mockMongoDatabase)
        val movie = movieSample()
        assertThrows<DatabaseInsertionException> { repository.insertMovie(movie) }
    }

    @Test
    fun `given a rate, should return all movies with the given rate`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
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
