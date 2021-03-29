package com.thesidh.zmovie.application.rest.controllers

import com.thesidh.zmovie.application.messaging.producer.SaveMovieProducer
import com.thesidh.zmovie.application.rest.exceptions.InvalidBodySuppliedException
import com.thesidh.zmovie.domain.entities.Movie
import io.javalin.Context
import java.lang.Exception

class ProduceMessageController(val producer: SaveMovieProducer) {
    @
    fun produceMessage(ctx: Context) {
        val movie = try {
                ctx.body<Movie>()
        } catch (e: Exception) {
            throw InvalidBodySuppliedException()
        }
        producer.produce(movie)
    }
}
