package com.thesidh.zmovie.domain.exceptions

class TooMuchActorsException : ZMovieException(){
    override fun message() = "The maximum allowed actors are 10"

}