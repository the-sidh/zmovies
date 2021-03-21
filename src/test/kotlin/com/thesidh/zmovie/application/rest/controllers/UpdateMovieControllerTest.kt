package com.thesidh.zmovie.application.rest.controllers

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.services.UpdateMovieService
import com.thesidh.zmovie.rest.controllers.UpdateMovieController
import com.thesidh.zmovie.rest.exceptions.InvalidBodySuppliedException
import com.thesidh.zmovie.storage.sample.movieSample
import io.javalin.Context
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UpdateMovieControllerTest {
    private val service = mockk<UpdateMovieService>()
    private val ctx = mockk<Context>(relaxed = true)

    @Test
    fun `when receive a request to update a movie should call the appropriate function on the service and return HTTP status 200`() {
        val movie = movieSample()
        every { ctx.body<Movie>() } returns movie
        every { ctx.pathParam("title") } returns movie.title
        every { service.updateMovie(title = movie.title, movie = movie) } just runs
        val controller = UpdateMovieController(service)
        controller.update(ctx)
        verify { service.updateMovie(title = movie.title, movie = movie) }
        verify { ctx.status(HttpStatus.NO_CONTENT_204) }
    }

    @Test
    fun `given the request was badly formatted, should throw an exception`() {
        every {ctx.body<Movie>()} throws Exception()
        val movie = movieSample()
        every { service.updateMovie(title = movie.title, movie = movie) } just runs
        val controller =
                UpdateMovieController(service)
        assertThrows<InvalidBodySuppliedException> {  controller.update(ctx) }
    }
}
