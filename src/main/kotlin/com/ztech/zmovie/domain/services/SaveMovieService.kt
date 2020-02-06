package com.ztech.zmovie.domain.services

import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.exceptions.TooMuchActorsException
import com.ztech.zmovie.domain.gateways.storage.MoviesRepository


class SaveMovieService(val repository: MoviesRepository) {
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