package com.ztech.zmovie.application.web.controllers

import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.services.SaveMovieService
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus

class SaveMoviesController(private val service: SaveMovieService) {
    fun save(ctx : Context){
        val movie = ctx.body<Movie>()
        val persisted = service.saveMovie(movie)
        ctx.json(persisted).status(HttpStatus.OK_200)
    }
}