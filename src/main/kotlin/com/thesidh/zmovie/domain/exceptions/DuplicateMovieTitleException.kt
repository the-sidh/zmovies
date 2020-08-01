package com.thesidh.zmovie.domain.exceptions

class DuplicateMovieTitleException : ZMovieException() {
    override fun message() = "A movie with this title already exists"
}