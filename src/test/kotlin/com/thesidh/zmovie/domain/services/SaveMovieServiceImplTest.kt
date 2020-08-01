package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.domain.exceptions.TooMuchActorsException
import com.thesidh.zmovie.domain.gateways.storage.MoviesRepository
import com.thesidh.zmovie.resources.storage.sample.movieSample
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.text.SimpleDateFormat

class SaveMovieServiceImplTest() {
    private val repository = mockk<MoviesRepository>(relaxed = true)
    @Test
    fun `Given a valid movie, should be able to save it`() {
        val service = SaveMovieService(repository)
        val movie = movieSample()
        every { repository.insertMovie(movie) } returns true
        Assertions.assertEquals(service.saveMovie(movie), movie)
    }

    @Test
    fun `Given that a movie has more than 10 actors, it should not be saved`() {
        val service = SaveMovieService(repository)
        val movie = Movie(
            title = "ET",
            director = "Steven Spielberg",
            releaseDate = SimpleDateFormat("yyyy-MM-dd").parse("1982-5-23"),
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
            ),
            rate = Rate.SEM_CENSURA
        )
        every { repository.insertMovie(movie) } returns true

        assertThrows<TooMuchActorsException> { service.saveMovie(movie) }
    }
}