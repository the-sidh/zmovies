package com.thesidh.zmovie.rest.controllers

import com.thesidh.zmovie.domain.services.RetrieveMovieService
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus

class RetrieveMovieController (private val retrieveMovieService: RetrieveMovieService){
    fun retrieveMovie(ctx:Context){
        val movieTitle =
                ctx.pathParam("title")

        val movie = retrieveMovieService.retrieveMovie(movieTitle)
        ctx.json(movie).status(HttpStatus.OK_200)
    }
}
