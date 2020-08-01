package com.thesidh.zmovie.domain.exceptions

class DatabaseInsertionException : ZMovieException(){
    override fun message() = "A database error occurred when trying to save the movie"

}
