package com.thesidh.zmovie.application.rest.routes

import com.thesidh.zmovie.application.rest.controllers.SaveMoviesController
import io.javalin.apibuilder.ApiBuilder

class SaveMovieRoute(val controller: SaveMoviesController) {
    fun register() {
        ApiBuilder.post(
            "/movie/",
            controller::save
        )
    }
}