package com.thesidh.zmovie.storage.mongodb

import com.mongodb.MongoException
import com.mongodb.MongoWriteException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.domain.exceptions.DatabaseInsertionException
import com.thesidh.zmovie.domain.exceptions.DuplicateMovieTitleException
import com.thesidh.zmovie.domain.exceptions.MovieNotFoundException
import com.thesidh.zmovie.domain.storage.MoviesRepository
import com.thesidh.zmovie.storage.mongodb.entities.MovieDocument
import org.bson.Document

class MongoDBMoviesRepository(mongoDatabase: MongoDatabase) :
        MoviesRepository {

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
            when (e) {
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

    override fun removeMovie(title: String): Boolean {
        val query = Document.parse(
                """{_id : "$title"}"""
        )
        return collection.deleteOne(query).deletedCount > 0
    }

    override fun retrieveMovie(title: String): Movie? {
        val condition = Filters.eq("_id", title)
        return collection.find(condition).first()?.let { MovieDocument.toMovie(it) }
    }

    override fun updateMovie(title: String, movie: Movie): Boolean {
        val condition = Filters.eq("_id", title)
        val doc = MovieDocument.fromMovie(movie)
        return collection.findOneAndReplace(condition, doc) != null
    }
}
