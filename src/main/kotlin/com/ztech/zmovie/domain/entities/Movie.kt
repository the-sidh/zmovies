package com.ztech.zmovie.domain.entities

import java.time.LocalDate

data class Movie(
    val title: String,
    val releaseDate: LocalDate,
    val rate: Rate,
    val director: String,
    val actors: List<String>
)