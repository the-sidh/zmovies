package com.thesidh.zmovie.rest.routes

import com.thesidh.zmovie.rest.controllers.RetrieveMoviesByRateController
import io.javalin.apibuilder.ApiBuilder

class RetrieveMoviesbyRateRoute(val retrieveMoviesByRateController: RetrieveMoviesByRateController) {
    fun register() {
        ApiBuilder.get(
            "/movies/:rate",
            retrieveMoviesByRateController::retrieve
        )
    }
}