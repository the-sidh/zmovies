package com.thesidh.zmovie.application.rest.controllers

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.services.UpdateMovieService
import com.thesidh.zmovie.application.rest.exceptions.InvalidBodySuppliedException
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus
import java.lang.Exception

class UpdateMovieController(private val updateMovieService: UpdateMovieService) {
    fun update(ctx: Context) {
        val title = ctx.pathParam("title")
        val movie = try {
            ctx.body<Movie>()
        } catch (e: Exception) {
            throw InvalidBodySuppliedException()
        }
        updateMovieService.updateMovie(title = title, movie = movie)
        ctx.status(HttpStatus.NO_CONTENT_204)
    }
}
