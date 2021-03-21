package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.exceptions.MovieNotFoundException
import com.thesidh.zmovie.domain.storage.MoviesRepository

class DeleteMoviesService(private val moviesRepository: MoviesRepository) {

    fun deleteMovie(title: String) {
        takeUnless {
            moviesRepository.removeMovie(title)
        }?.apply {
            throw MovieNotFoundException() }
    }
}
