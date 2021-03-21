package com.thesidh.zmovie.application.rest.controllers

import com.thesidh.zmovie.domain.services.RetrieveMovieService
import com.thesidh.zmovie.storage.sample.movieSample
import io.javalin.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.Test

class RetrieveMovieControllerTest {
    private val service = mockk<RetrieveMovieService>()
    private val ctx = mockk<Context>(relaxed = true)

    @Test
    fun `when receive a request to retrieve a movie should call the appropriate function on the service and return HTTP status 200 with the movie as content`() {
        val movie = movieSample()
        val movieTitle = movie.title
        every { ctx.pathParam("title") } returns movieTitle
        every { service.retrieveMovie(movieTitle) } returns movie
        val controller = RetrieveMovieController(service)
        controller.retrieveMovie(ctx)
        verify { service.retrieveMovie(movieTitle) }
        verify { ctx.json(movie).status(HttpStatus.OK_200) }
    }
}