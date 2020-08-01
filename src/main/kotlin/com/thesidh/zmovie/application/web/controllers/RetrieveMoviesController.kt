package com.thesidh.zmovie.application.web.controllers

import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.domain.exceptions.InvalidBodySuppliedException
import com.thesidh.zmovie.domain.services.RetrieveMoviesService
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus
import java.lang.Exception

class RetrieveMoviesController(private val service: RetrieveMoviesService) {
    fun retrieve(ctx: Context) {
        val rate = try {
            Rate.valueOf(ctx.pathParam("rate"))
        } catch (e: Exception) {
            throw InvalidBodySuppliedException()
        }
        val persisted = service.retrieveMovies(rate)
        ctx.json(persisted).status(HttpStatus.OK_200)

    }
}