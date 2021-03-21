package com.thesidh.zmovie.application.rest.controllers

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.application.rest.exceptions.InvalidBodySuppliedException
import com.thesidh.zmovie.domain.services.SaveMovieService
import com.thesidh.zmovie.storage.sample.movieSample
import io.javalin.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SaveMoviesControllerTest {
    private val ctx = mockk<Context>(relaxed = true)
    @Test
    fun `given the request was well formatted, should save a movie`() {
        val service = mockk<SaveMovieService>()
        val movie = movieSample()
        every {ctx.body<Movie>()}returns movie
        every { service.saveMovie(movie) } returns movie
        val controller =
            SaveMoviesController(service)
        controller.save(ctx)
        verify(exactly = 1) { service.saveMovie(any()) }
    }
    @Test
    fun `given the request was bad formatted, should throw an exception`() {
        val service = mockk<SaveMovieService>()
        every {ctx.body<Movie>()} throws Exception()
        val movie = movieSample()
        every { service.saveMovie(movie) } returns movie
        val controller =
            SaveMoviesController(service)
        assertThrows<InvalidBodySuppliedException> {  controller.save(ctx) }
    }
}