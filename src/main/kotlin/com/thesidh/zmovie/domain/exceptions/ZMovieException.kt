package com.thesidh.zmovie.domain.exceptions

abstract class ZMovieException : Exception {

    constructor() : super()
    abstract fun message(): String

}

data class Error(val message: String)

