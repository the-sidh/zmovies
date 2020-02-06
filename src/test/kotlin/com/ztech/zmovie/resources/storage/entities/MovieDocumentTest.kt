package com.ztech.zmovie.resources.storage.entities

import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.resources.storage.mongodb.entities.MovieDocument
import com.ztech.zmovie.resources.storage.sample.movieDocumentSample
import com.ztech.zmovie.resources.storage.sample.movieSample
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.function.Executable
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat


class MovieDocumentTest {
    @Test
    fun `given a valid MovieDocument, should return a Movie`() {
        val doc = movieDocumentSample()

        val movie = MovieDocument.toMovie(doc)
        Assertions.assertAll(
            Executable { Assertions.assertEquals(movie.title, doc.id) },
            Executable { Assertions.assertEquals(movie.director, doc.director) },
            Executable { Assertions.assertEquals(movie.releaseDate, doc.releaseDate) },
            Executable { Assertions.assertEquals(movie.actors, doc.actors) },
            Executable { Assertions.assertEquals(movie.rate, doc.rate) }
        )
    }

    @Test
    fun `given a invalid MovieDocument, should throw an exception`() {
        val doc = MovieDocument(
            id = "ET",
            director = null,
            releaseDate = SimpleDateFormat("yyyy-MM-dd").parse("1982-5-23"),
            actors = listOf("Drew Barrymore", "Henry Thomas"),
            rate = Rate.SEM_CENSURA
        )
        assertThrows<IllegalArgumentException> { MovieDocument.toMovie(doc) }
    }

    @Test
    fun `given a valid Movie, should create a MovieDocument`() {
        val movie = movieSample()
        val doc = MovieDocument.fromMovie(movie)
        Assertions.assertAll(
            Executable { Assertions.assertEquals(movie.title, doc.id) },
            Executable { Assertions.assertEquals(movie.director, doc.director) },
            Executable { Assertions.assertEquals(movie.releaseDate, doc.releaseDate) },
            Executable { Assertions.assertEquals(movie.actors, doc.actors) },
            Executable { Assertions.assertEquals(movie.rate, doc.rate) }
        )
    }

}