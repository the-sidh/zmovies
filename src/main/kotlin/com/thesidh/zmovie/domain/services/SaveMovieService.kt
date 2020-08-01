package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.exceptions.TooMuchActorsException
import com.thesidh.zmovie.domain.storage.MoviesRepository


class SaveMovieService(private val repository: MoviesRepository) {
    fun saveMovie(movie: Movie): Movie {
        when {
            movie.actors.size > 10 -> throw TooMuchActorsException()
            else -> {
                repository.insertMovie(movie)
                return movie
            }
        }
    }
}