package com.thesidh.zmovie.rest.controllers

import com.thesidh.zmovie.domain.services.DeleteMoviesService
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus

class DeleteMoviesController(private val deleteMoviesService: DeleteMoviesService) {

    fun deleteMovie(ctx: Context) {
        val movieTitle =
            ctx.pathParam("title")

        deleteMoviesService.deleteMovie(movieTitle)
        ctx.status(HttpStatus.NO_CONTENT_204)
    }

}