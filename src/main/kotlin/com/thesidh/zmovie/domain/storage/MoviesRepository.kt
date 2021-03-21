package com.thesidh.zmovie.domain.storage

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.entities.Rate

interface MoviesRepository {
    fun insertMovie(movie: Movie): Boolean
    fun retrieveByRate(rate: Rate): List<Movie>
    fun removeMovie(title: String): Boolean
    fun updateMovie(title: String, movie: Movie): Boolean
}