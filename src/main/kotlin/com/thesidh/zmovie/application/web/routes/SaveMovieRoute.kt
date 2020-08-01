package com.thesidh.zmovie.application.web.routes

import com.thesidh.zmovie.application.web.controllers.SaveMoviesController
import io.javalin.apibuilder.ApiBuilder

class SaveMovieRoute(val controller: SaveMoviesController) {
    fun register() {
        ApiBuilder.post(
            "/movie/",
            controller::save
        )
    }
}