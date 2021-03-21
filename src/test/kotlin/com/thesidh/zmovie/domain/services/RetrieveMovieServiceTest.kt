package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.exceptions.MovieNotFoundException
import com.thesidh.zmovie.domain.storage.MoviesRepository
import com.thesidh.zmovie.storage.sample.movieSample
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RetrieveMovieServiceTest {

    private val repo = mockk<MoviesRepository>(relaxed = true)

    @Test
    fun `given a movie title that caused the repository to return a null object when retrieving a movie should throw an exception`() {
        val movie = movieSample()
        val service = RetrieveMovieService(repository = repo)
        every { repo.retrieveMovie(movie.title) } returns null
        assertThrows<MovieNotFoundException> { service.retrieveMovie(movie.title) }
    }

    @Test
    fun `given persisted movie title when retrieving a movie should retrieve the movie`() {
        val movie = movieSample()
        val service = RetrieveMovieService(repository = repo)
        every { repo.retrieveMovie(movie.title) } returns movie
        val retrievedMovie = service.retrieveMovie(movie.title)
        assertEquals(movie, retrievedMovie)
    }
}