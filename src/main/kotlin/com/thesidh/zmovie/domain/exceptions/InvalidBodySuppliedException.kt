package com.thesidh.zmovie.domain.exceptions

import com.thesidh.zmovie.domain.exceptions.APIException
import org.eclipse.jetty.http.HttpStatus

class InvalidBodySuppliedException : APIException() {
    override fun httpStatus()= HttpStatus.UNPROCESSABLE_ENTITY_422

    override fun userResponseMessage()= "Supplied body was invalid"

}