package com.ztech.zmovie.resources.storage.mongodb

import com.mongodb.MongoException
import com.mongodb.MongoWriteException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.domain.exceptions.DatabaseInsertionException
import com.ztech.zmovie.domain.exceptions.DuplicateMovieTitleException
import com.ztech.zmovie.domain.gateways.storage.MoviesRepository
import com.ztech.zmovie.resources.storage.mongodb.entities.MovieDocument
import org.bson.Document

class MoviesRepositoryImpl(mongoDatabase: MongoDatabase) : MoviesRepository {

    private val collection: MongoCollection<MovieDocument> =
        mongoDatabase.getCollection(
            "zmovie",
            MovieDocument::class.java
        )

    override fun insertMovie(movie: Movie): Boolean {
        try {
            val doc = MovieDocument.fromMovie(movie)
            collection.insertOne(doc)
            return true
        } catch (e: MongoException) {
            when (e){
                is MongoWriteException -> throw DuplicateMovieTitleException()
                else -> throw DatabaseInsertionException()
            }

        }
    }

    override fun retrieveByRate(rate: Rate): List<Movie> {
        val query = Document.parse(
            """{rate : "$rate"}"""
        )
        val movies = mutableListOf<Movie>()
        val docMovies = collection.find(query)

        for (doc in docMovies) {
            movies.add((MovieDocument.toMovie(doc)))
        }
        return movies
    }
}