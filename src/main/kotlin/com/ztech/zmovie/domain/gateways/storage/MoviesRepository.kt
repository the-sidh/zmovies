package com.ztech.zmovie.domain.gateways.storage

import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.entities.Rate

interface MoviesRepository{
    fun insertMovie(movie: Movie):Boolean
    fun retrieveByRate(rate: Rate):List<Movie>
}