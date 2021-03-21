package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.exceptions.MovieNotFoundException
import com.thesidh.zmovie.domain.exceptions.ZMovieException
import com.thesidh.zmovie.domain.storage.MoviesRepository
import com.thesidh.zmovie.storage.sample.movieSample
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.apache.http.util.Asserts
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeleteMoviesServiceTest {

    private val repository = mockk<MoviesRepository>(relaxed = true)

    @Test
    fun `given a movie, when deleting it, should call the remove function on the repository`() {
        val movie = movieSample()
        every { repository.removeMovie(movie.title) } returns true
        val service = DeleteMoviesService(repository)
        service.deleteMovie(movie.title)
        verify { repository.removeMovie(movie.title) }
    }

    @Test
    fun `given a movie, when failing to delete, should throw an exception`() {
        val movie = movieSample()
        every { repository.removeMovie(movie.title) } returns false
        val service = DeleteMoviesService(repository)
        assertThrows<MovieNotFoundException> { service.deleteMovie(movie.title) }
    }
}
