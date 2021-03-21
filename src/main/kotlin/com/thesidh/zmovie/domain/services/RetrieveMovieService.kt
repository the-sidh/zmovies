package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.exceptions.MovieNotFoundException
import com.thesidh.zmovie.domain.storage.MoviesRepository

class RetrieveMovieService(private val repository: MoviesRepository) {
    fun retrieveMovie(title: String) =
            repository.retrieveMovie(title) ?: throw MovieNotFoundException()
}
