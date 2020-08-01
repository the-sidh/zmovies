package com.thesidh.zmovie.rest.routes

import com.thesidh.zmovie.rest.controllers.RetrieveMoviesController
import io.javalin.apibuilder.ApiBuilder

class RetrieveMoviesRoute(val controller: RetrieveMoviesController) {
    fun register() {
        ApiBuilder.get(
            "/movies/:rate",
            controller::retrieve
        )
    }
}