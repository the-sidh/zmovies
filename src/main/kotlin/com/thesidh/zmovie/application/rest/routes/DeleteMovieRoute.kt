package com.thesidh.zmovie.application.rest.routes

import com.thesidh.zmovie.application.rest.controllers.DeleteMoviesController
import io.javalin.apibuilder.ApiBuilder

class DeleteMovieRoute(private val controller: DeleteMoviesController) {
    fun register() {
        ApiBuilder.delete(
                "/movie/:title",
                controller::deleteMovie
        )
    }
}