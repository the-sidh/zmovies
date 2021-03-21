package com.thesidh.zmovie.application.rest.exceptions

import com.thesidh.zmovie.domain.exceptions.ZMovieException

class InvalidBodySuppliedException : ZMovieException() {
    override fun message()= "Supplied body was invalid"

}