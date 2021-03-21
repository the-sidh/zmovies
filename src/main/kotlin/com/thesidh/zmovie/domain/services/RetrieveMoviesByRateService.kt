package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.domain.storage.MoviesRepository

class RetrieveMoviesByRateService(private val repository: MoviesRepository) {
    fun retrieveMovies(rate: Rate) = repository.retrieveByRate(rate)
}
