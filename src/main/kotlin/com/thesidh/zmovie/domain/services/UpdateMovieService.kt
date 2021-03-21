package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.exceptions.MovieNotFoundException
import com.thesidh.zmovie.domain.storage.MoviesRepository

class UpdateMovieService(private val repo: MoviesRepository) {

    fun updateMovie(title: String, movie: Movie) {
        takeUnless {
            repo.updateMovie(title = title, movie = movie)
        }?.apply {
            throw MovieNotFoundException()
        }
    }
}
