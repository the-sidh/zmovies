package com.thesidh.zmovie.application.web.controllers

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.exceptions.InvalidBodySuppliedException
import com.thesidh.zmovie.domain.services.SaveMovieService
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus
import java.lang.Exception

class SaveMoviesController(private val service: SaveMovieService) {
    fun save(ctx: Context) {
        val movie = try {
            ctx.body<Movie>()
        } catch (e: Exception) {
            throw InvalidBodySuppliedException()
        }
        val persisted = service.saveMovie(movie)
        ctx.json(persisted).status(HttpStatus.OK_200)

    }
}