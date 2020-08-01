package com.thesidh.zmovie.domain.services

import com.thesidh.zmovie.domain.entities.Movie
import com.thesidh.zmovie.domain.entities.Rate
import com.thesidh.zmovie.domain.storage.MoviesRepository
import com.thesidh.zmovie.storage.sample.movieSample
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RetrieveMoviesServiceTest {

    private val repository = mockk<MoviesRepository>(relaxed = true)

    @Test
    fun `given a Rate, should return all the movies from the given rate`(){
        val service = RetrieveMoviesService(repository)
        val movieList = listOf<Movie>(
            movieSample(),
            movieSample(),
            movieSample(),
            movieSample(),
            movieSample()
        )

        every { repository.retrieveByRate(Rate.SEM_CENSURA) } returns movieList
        Assertions.assertEquals(service.retrieveMovies(Rate.SEM_CENSURA).size, 5)
    }
}