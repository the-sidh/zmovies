package com.ztech.zmovie.resources.storage.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.resources.storage.extension.MongoDBExtension
import com.ztech.zmovie.resources.storage.mongodb.MoviesRepositoryImpl
import com.ztech.zmovie.resources.storage.mongodb.entities.MovieDocument
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import java.time.LocalDate
import javax.swing.text.Document

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
    fun `given a valid movie, should persist it`() {
        val repository = MoviesRepositoryImpl(mongoDatabase)
        val movie = Movie(
            id = "rrrrr",
            title = "ET",
            director = "Steven Spielberg",
            releaseDate = LocalDate.of(1982, 5, 23),
            actors = listOf("Drew Barrymore", "Henry Thomas"),
            rate = Rate.SEM_CENSURA
        )
        repository.insertMovie(movie)

      val inserted = collection.find().first()
        Assertions.assertAll(
            Executable { Assertions.assertEquals(movie.title, inserted.title) },
            Executable { Assertions.assertEquals(movie.director, inserted.director) },
            Executable { Assertions.assertEquals(movie.releaseDate, inserted.releaseDate) },
            Executable { Assertions.assertEquals(movie.actors, inserted.actors) },
            Executable { Assertions.assertEquals(movie.rate, inserted.rate) }
        )
    }
}