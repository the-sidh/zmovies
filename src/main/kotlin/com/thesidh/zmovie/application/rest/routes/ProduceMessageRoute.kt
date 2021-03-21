package com.thesidh.zmovie.application.rest.routes

import com.thesidh.zmovie.application.rest.controllers.ProduceMessageController
import io.javalin.apibuilder.ApiBuilder

class ProduceMessageRoute(val controller: ProduceMessageController){
    fun register() {
        ApiBuilder.post(
            "/message",
            controller::produceMessage
        )
    }
}