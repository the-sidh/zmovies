package com.ztech.zmovie.domain.services

import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.domain.gateways.storage.MoviesRepository

class RetrieveMoviesService(val repository: MoviesRepository) {
    fun retrieveMovies(rate: Rate) = repository.retrieveByRate(rate)
}
