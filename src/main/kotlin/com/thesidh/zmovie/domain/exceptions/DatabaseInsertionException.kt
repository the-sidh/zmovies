package com.thesidh.zmovie.domain.exceptions

import com.thesidh.zmovie.domain.exceptions.APIException
import org.eclipse.jetty.http.HttpStatus

class DatabaseInsertionException : APIException(){
    override fun httpStatus() = HttpStatus.BAD_GATEWAY_502
    override fun userResponseMessage() = "A database error occurred when trying to save the movie"

}
