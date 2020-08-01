package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.domain.storage.MoviesRepository

class RetrieveMoviesService(val repository: MoviesRepository) {
    fun retrieveMovies(rate: Rate) = repository.retrieveByRate(rate)
}
