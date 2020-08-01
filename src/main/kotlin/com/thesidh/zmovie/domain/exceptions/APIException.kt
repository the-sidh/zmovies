package com.thesidh.zmovie.domain.exceptions

import io.javalin.Context

abstract class APIException : Exception {

    constructor() : super()

    abstract fun httpStatus(): Int
    abstract fun userResponseMessage(): String
    fun handleError(ctx: Context) {
        ctx.json(Error(userResponseMessage())).status(httpStatus())
    }
}

data class Error(val message: String)

