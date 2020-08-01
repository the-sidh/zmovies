package com.thesidh.zmovie.domain.exceptions

import com.thesidh.zmovie.domain.exceptions.APIException
import org.eclipse.jetty.http.HttpStatus

class DuplicateMovieTitleException : APIException() {
    override fun httpStatus() = HttpStatus.CONFLICT_409

    override fun userResponseMessage() = "A movie with this title already exists"
}