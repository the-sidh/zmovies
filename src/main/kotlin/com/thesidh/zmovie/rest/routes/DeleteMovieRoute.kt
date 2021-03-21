package com.thesidh.zmovie.rest.routes

import com.thesidh.zmovie.rest.controllers.DeleteMoviesController
import com.thesidh.zmovie.rest.controllers.SaveMoviesController
import io.javalin.apibuilder.ApiBuilder

class DeleteMovieRoute(private val controller: DeleteMoviesController) {
    fun register() {
        ApiBuilder.delete(
                "/movie/:title",
                controller::deleteMovie
        )
    }
}