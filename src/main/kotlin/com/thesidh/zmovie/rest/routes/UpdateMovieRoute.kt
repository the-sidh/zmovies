package com.thesidh.zmovie.rest.routes

import com.thesidh.zmovie.rest.controllers.UpdateMovieController
import io.javalin.apibuilder.ApiBuilder


class UpdateMovieRoute(private val controller: UpdateMovieController) {
    fun register() {
        ApiBuilder.put(
                "/movie/:title",
                controller::update
        )
    }
}