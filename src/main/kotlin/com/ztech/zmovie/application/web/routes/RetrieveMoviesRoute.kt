package com.ztech.zmovie.application.web.routes

import com.ztech.zmovie.application.web.controllers.RetrieveMoviesController
import io.javalin.apibuilder.ApiBuilder

class RetrieveMoviesRoute(val controller: RetrieveMoviesController) {
    fun register() {
        ApiBuilder.get(
            "/movies/:rate",
            controller::retrieve
        )
    }
}