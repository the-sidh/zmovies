package com.thesidh.zmovie.application.rest.routes

import com.thesidh.zmovie.application.rest.controllers.RetrieveMovieController
import io.javalin.apibuilder.ApiBuilder

class RetrieveMovieRoute(val controller: RetrieveMovieController) {
    fun register() {
        ApiBuilder.get(
                "/movie/:title",
                controller::retrieveMovie
        )
    }
}