package com.thesidh.zmovie.storage.repositories

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.domain.exceptions.DatabaseInsertionException
import com.thesidh.zmovie.domain.exceptions.DuplicateMovieTitleException
import com.thesidh.zmovie.domain.exceptions.MovieNotFoundException
import com.thesidh.zmovie.storage.extension.MongoDBExtension
import com.thesidh.zmovie.storage.sample.movieSample
import com.thesidh.zmovie.storage.mongodb.MongoDBMoviesRepository
import com.thesidh.zmovie.storage.mongodb.entities.MovieDocument
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
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
    fun `given that a movie has already been persisted, other movie with the same title should result in a exception`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie1 = movieSample()
        val movie2 = movieSample()
        repository.insertMovie(movie1)
        assertThrows<DuplicateMovieTitleException> { repository.insertMovie(movie2) }
    }

    @Test
    fun `given that a movie has already been persisted, when deleting the movie should remove from the database and return true`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie1 = movieSample()
        repository.insertMovie(movie1)
        val result = repository.removeMovie(movie1.title)
        assertTrue(result)
        assertNull(collection.find().first())
    }

    @Test
    fun `given that a movie has not been persisted, when deleting the movie should remove from the database and return false`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie1 = movieSample()
        val result = repository.removeMovie(movie1.title)
        assertFalse(result)
    }

    @Test
    fun `given a valid movie, should persist it`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie = movieSample()
        val wasInserted = repository.insertMovie(movie)

        val inserted = collection.find().first()
        assertAll(
                Executable { assertTrue(wasInserted) },
                Executable { assertEquals(movie.title, inserted?.id) },
                Executable { assertEquals(movie.director, inserted?.director) },
                Executable { assertEquals(movie.releaseDate, inserted?.releaseDate) },
                Executable { assertEquals(movie.actors, inserted?.actors) },
                Executable { assertEquals(movie.rate, inserted?.rate) }
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
    fun `given a movie, should return all movies with the given rate`() {
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
        assertAll(
                Executable { assertEquals(movies.size, 2) }
        )
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
        assertAll(
                Executable { assertEquals(movies.size, 2) }
        )
    }

    @Test
    fun `given an existing movie the database and a new movie object, when updating should replace the older data with the new data`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie = movieSample()
        repository.insertMovie(movie)
        val movie2 = Movie(
                title = "ET",
                director = "Steven Spielberg",
                releaseDate = SimpleDateFormat("yyyy-MM-dd").parse("1982-5-23"),
                actors = listOf("Drew Barrymore", "Henry Thomas", "Peter Coyote"),
                rate = Rate.SEM_CENSURA
        )
        repository.updateMovie(title = movie.title, movie = movie2)

        val movies = repository.retrieveByRate(Rate.SEM_CENSURA)
        assertAll(
                Executable {
                    assertTrue(movies[0].actors.contains("Peter Coyote"))
                }
        )
    }

    @Test
    fun `given a non-existing movie the database and a new movie object, when trying to update should return false`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie2 = Movie(
                title = "ET",
                director = "Steven Spielberg",
                releaseDate = SimpleDateFormat("yyyy-MM-dd").parse("1982-5-23"),
                actors = listOf("Drew Barrymore", "Henry Thomas", "Peter Coyote"),
                rate = Rate.SEM_CENSURA
        )
        assertFalse { repository.updateMovie(movie2.title, movie2) }
    }

    @Test
    fun `given an existing movie the database, when updating should replace the older data with the new data`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie = movieSample()
        repository.insertMovie(movie)
        val movie2 = Movie(
                title = "ET",
                director = "Steven Spielberg",
                releaseDate = SimpleDateFormat("yyyy-MM-dd").parse("1982-5-23"),
                actors = listOf("Drew Barrymore", "Henry Thomas", "Peter Coyote"),
                rate = Rate.SEM_CENSURA
        )
        repository.updateMovie(title = movie.title, movie = movie2)

        val movies = repository.retrieveByRate(Rate.SEM_CENSURA)
        assertAll(
                Executable {
                    assertTrue(movies[0].actors.contains("Peter Coyote"))
                }
        )
    }

    @Test
    fun `given the title of an existing movie in the database when retrieving the movie should return the movie`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie = movieSample()
        repository.insertMovie(movie)
        val movie2 = repository.retrieveMovie(title = movie.title)
        assertEquals(movie, movie2)
    }

    @Test
    fun `given the title of an non-existing movie in the database when retrieving the movie should return null`() {
        val repository = MongoDBMoviesRepository(mongoDatabase)
        val movie = movieSample()
        assertNull(repository.retrieveMovie(title = movie.title))
    }
}
