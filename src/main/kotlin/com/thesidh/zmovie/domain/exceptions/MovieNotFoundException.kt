package com.thesidh.zmovie.domain.exceptions

class MovieNotFoundException : ZMovieException(){
    override fun message() = "The movie was not found"
}
