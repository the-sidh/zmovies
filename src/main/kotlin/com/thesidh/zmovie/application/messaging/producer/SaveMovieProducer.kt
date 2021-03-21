package com.thesidh.zmovie.application.messaging.producer

import com.thesidh.zmovie.application.config.JsonParserBuilder.getObjectMapper
import com.thesidh.zmovie.domain.entities.Movie
import javax.jms.MessageProducer
import javax.jms.Queue
import javax.jms.Session

class SaveMovieProducer(private val buildSession: Session, private val queue: Queue) {
    private val producer: MessageProducer = buildSession.createProducer(queue)
    fun produce(movie: Movie) {
        val message = buildSession.createTextMessage(getObjectMapper().writeValueAsString(movie))
        producer.send(message)
    }
}