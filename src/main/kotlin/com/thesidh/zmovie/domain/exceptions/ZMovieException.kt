package com.thesidh.zmovie.domain.exceptions

abstract class ZMovieException() : Exception() {

    abstract fun message(): String

}

data class Error(val message: String)

