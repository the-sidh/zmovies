package com.ztech.zmovie.application

import io.javalin.Javalin

fun main(args: Array<String>) {

    val app = Javalin.create().apply {

        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start(7000)

    app.routes {

    }
}