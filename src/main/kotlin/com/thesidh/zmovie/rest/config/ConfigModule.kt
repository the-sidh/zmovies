package com.thesidh.zmovie.rest.config

import com.thesidh.zmovie.storage.mongodb.config.mongoDatabase
import com.thesidh.zmovie.rest.controllers.RetrieveMoviesController
import com.thesidh.zmovie.rest.controllers.SaveMoviesController
import com.thesidh.zmovie.rest.routes.RetrieveMoviesRoute
import com.thesidh.zmovie.rest.routes.SaveMovieRoute
import com.thesidh.zmovie.domain.storage.MoviesRepository
import com.thesidh.zmovie.domain.services.RetrieveMoviesService
import com.thesidh.zmovie.domain.services.SaveMovieService
import com.thesidh.zmovie.storage.mongodb.MongoDBMoviesRepository
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val saveMovieRoutesModule: Module = module {
    single { SaveMovieRoute(get()) }
}

val retrieveMoviesRoutesModule: Module = module {
    single { RetrieveMoviesRoute(get()) }
}

val saveMovieControllersModule: Module = module {
    single { SaveMoviesController(get()) }
}

val retrieveMoviesControllersModule: Module = module {
    single { RetrieveMoviesController(get()) }
}

val saveMovieServiceModule: Module = module {
    single { SaveMovieService(get()) }
}

val retrieveMoviesServiceModule: Module = module {
    single { RetrieveMoviesService(get()) }
}

val repositoryModules: Module = module {
    single { MongoDBMoviesRepository(get()) as MoviesRepository }
}

val mongoModule: Module = module {
    single {
        mongoDatabase(
            connectionString = "localhost:27017",
            user = "zmovies",
            password = "zmovies",
            databaseName = "zmovies"
        )
    }
}