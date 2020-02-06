package com.ztech.zmovie.domain.gateways.storage

import com.ztech.zmovie.domain.entities.Movie

interface MoviesRepository{
    fun insertMovie(movie: Movie)
}