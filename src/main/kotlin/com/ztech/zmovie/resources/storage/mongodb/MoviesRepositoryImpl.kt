package com.ztech.zmovie.resources.storage.mongodb

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.gateways.storage.MoviesRepository
import com.ztech.zmovie.resources.storage.mongodb.entities.MovieDocument

class MoviesRepositoryImpl(mongoDatabase: MongoDatabase) : MoviesRepository{

    private val collection: MongoCollection<MovieDocument> =
        mongoDatabase.getCollection(
            "zmovie",
            MovieDocument::class.java
        )

    override fun insertMovie(movie: Movie) {
        val doc = MovieDocument.fromMovie(movie)
        collection.insertOne(doc)
    }



}