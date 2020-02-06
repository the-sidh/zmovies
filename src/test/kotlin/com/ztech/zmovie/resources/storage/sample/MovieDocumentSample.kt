package com.ztech.zmovie.resources.storage.sample

import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.resources.storage.mongodb.entities.MovieDocument
import java.text.SimpleDateFormat

fun movieDocumentSample() = MovieDocument(
    id = "ET",
    director = "Steven Spielberg",
    releaseDate = SimpleDateFormat("yyyy-MM-dd").parse("1982-5-23"),
    actors = listOf("Drew Barrymore", "Henry Thomas"),
    rate = Rate.SEM_CENSURA
)