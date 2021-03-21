package com.thesidh.zmovie.application.rest.controllers

import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.application.rest.exceptions.InvalidBodySuppliedException
import com.thesidh.zmovie.domain.services.RetrieveMoviesByRateService
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus
import java.lang.Exception

class RetrieveMoviesByRateController(private val retrieveMoviesByRateService: RetrieveMoviesByRateService) {
    fun retrieve(ctx: Context) {
        val rate = try {
            Rate.valueOf(ctx.pathParam("rate"))
        } catch (e: Exception) {
            throw InvalidBodySuppliedException()
        }
        val movies = retrieveMoviesByRateService.retrieveMovies(rate)
        ctx.json(movies).status(HttpStatus.OK_200)
    }
}
