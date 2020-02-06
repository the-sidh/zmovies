package com.ztech.zmovie.application.web.controllers

import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.domain.services.SaveMovieService
import com.ztech.zmovie.resources.storage.sample.movieSample
import io.javalin.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SaveMoviesControllerTest {
    private val ctx = mockk<Context>(relaxed = true)
    @Test
    fun `should save a movie`() {
        val service = mockk<SaveMovieService>()
        val movie = movieSample()
        every {ctx.body<Movie>()}returns movie
        every { service.saveMovie(movie) } returns movie
        val controller = SaveMoviesController(service)
        controller.save(ctx)
        verify(exactly = 1) { service.saveMovie(any()) }
    }
}