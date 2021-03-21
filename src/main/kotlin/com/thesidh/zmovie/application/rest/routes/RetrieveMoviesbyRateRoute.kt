package com.thesidh.zmovie.application.rest.routes

import com.thesidh.zmovie.application.rest.controllers.RetrieveMoviesByRateController
import io.javalin.apibuilder.ApiBuilder

class RetrieveMoviesbyRateRoute(val retrieveMoviesByRateController: RetrieveMoviesByRateController) {
    fun register() {
        ApiBuilder.get(
            "/movies/:rate",
            retrieveMoviesByRateController::retrieve
        )
    }
}