package com.ztech.zmovie.domain.exceptions

abstract class APIException : Exception {

    constructor() : super()

    abstract fun httpStatus(): Int
    abstract fun userResponseMessage(): String
}

