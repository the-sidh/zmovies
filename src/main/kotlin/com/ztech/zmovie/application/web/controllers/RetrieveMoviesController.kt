package com.ztech.zmovie.application.web.controllers

import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.domain.exceptions.InvalidBodySuppliedException
import com.ztech.zmovie.domain.services.RetrieveMoviesService
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