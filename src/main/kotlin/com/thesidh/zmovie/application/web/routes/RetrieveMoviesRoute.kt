package com.thesidh.zmovie.application.web.routes

import com.thesidh.zmovie.application.web.controllers.RetrieveMoviesController
import io.javalin.apibuilder.ApiBuilder

class RetrieveMoviesRoute(val controller: RetrieveMoviesController) {
    fun register() {
        ApiBuilder.get(
            "/movies/:rate",
            controller::retrieve
        )
    }
}