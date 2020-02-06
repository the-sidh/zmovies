package com.ztech.zmovie.domain.exceptions

import org.eclipse.jetty.http.HttpStatus

class TooMuchActorsException : APIException(){
    override fun httpStatus() = HttpStatus.BAD_REQUEST_400

    override fun userResponseMessage() = "The maximum allowed actors are 10"

}