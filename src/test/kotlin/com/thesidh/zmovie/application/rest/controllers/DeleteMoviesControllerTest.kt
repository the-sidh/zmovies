package com.thesidh.zmovie.application.rest.controllers

import com.thesidh.zmovie.domain.services.DeleteMoviesService
import com.thesidh.zmovie.storage.sample.movieSample
import io.javalin.Context
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.Test

class DeleteMoviesControllerTest {

    private val service = mockk<DeleteMoviesService>()
    private val ctx = mockk<Context>(relaxed = true)

    @Test
    fun `when receive a request to delete a movie should call the appropriate function on the service and return HTTP status 200`() {
        val movieTitle = movieSample().title
        every { ctx.pathParam("title") } returns movieTitle
        every { service.deleteMovie(movieTitle) } just runs
        val controller = DeleteMoviesController(service)
        controller.deleteMovie(ctx)
        verify { service.deleteMovie(movieTitle) }
        verify { ctx.status(HttpStatus.NO_CONTENT_204) }
    }
}