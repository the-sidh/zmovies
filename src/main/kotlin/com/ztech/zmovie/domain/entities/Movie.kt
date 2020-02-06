package com.ztech.zmovie.domain.entities

import java.time.LocalDate
import java.util.*

data class Movie(
    val title: String,
    val releaseDate: Date,
    val rate: Rate,
    val director: String,
    val actors: List<String>
)