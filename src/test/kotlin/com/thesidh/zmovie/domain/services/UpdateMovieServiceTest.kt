package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.exceptions.MovieNotFoundException
import com.thesidh.zmovie.domain.storage.MoviesRepository
import com.thesidh.zmovie.storage.sample.movieSample
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UpdateMovieServiceTest {
    private val repo = mockk<MoviesRepository>(relaxed = true)

    @Test
    fun `given an existing movie the database and a new movie object, when trying to update should call the function on the repository to update the movie`() {
        val movie = movieSample()
        every { repo.updateMovie(title = movie.title, movie = movie) } returns true
        val service = UpdateMovieService(repo = repo)
        service.updateMovie(title = movie.title, movie = movie)
        verify { repo.updateMovie(title = movie.title, movie = movie) }
    }

    @Test
    fun `given a non-existing movie the database and a new movie object, when trying to update should throw and exception`() {
        val movie = movieSample()
        every { repo.updateMovie(title = movie.title, movie = movie) } returns false
        val service = UpdateMovieService(repo = repo)
        assertThrows<MovieNotFoundException> { service.updateMovie(title = movie.title, movie = movie) }
    }

}