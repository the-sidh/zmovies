package com.ztech.zmovie.domain.exceptions

import org.eclipse.jetty.http.HttpStatus

class InvalidBodySupplied : APIException() {
    override fun httpStatus()= HttpStatus.UNPROCESSABLE_ENTITY_422

    override fun userResponseMessage()= "Supplied body was invalid"

}