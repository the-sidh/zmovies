package com.thesidh.zmovie.rest.controllers

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.services.DeleteMoviesService
import com.thesidh.zmovie.rest.exceptions.InvalidBodySuppliedException
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus
import java.lang.Exception

class DeleteMoviesController(private val deleteMoviesService: DeleteMoviesService) {

    fun deleteMovie(ctx: Context) {
        val movieTitle =
            ctx.pathParam("title")

        deleteMoviesService.deleteMovie(movieTitle)
        ctx.status(HttpStatus.NO_CONTENT_204)
    }

}