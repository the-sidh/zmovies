package com.thesidh.zmovie.application.messaging.consumer

import com.thesidh.zmovie.application.config.JsonParserBuilder.getObjectMapper
import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.services.SaveMovieService
import javax.jms.*

class SaveMovieConsumer(
    val buildSession: Session,
    val queue: Queue,
    private val service: SaveMovieService
) : MessageListener {

    fun start() {
        buildSession.createConsumer(queue).messageListener = this
    }

    override fun onMessage(message: Message?) {
        val movie = getObjectMapper().readValue((message as TextMessage).text, Movie::class.java)
        service.saveMovie(movie)
    }
}
