package com.thesidh.zmovie.application.config.modules

import com.thesidh.zmovie.application.config.mongoDatabase
import com.thesidh.zmovie.application.web.controllers.RetrieveMoviesController
import com.thesidh.zmovie.application.web.controllers.SaveMoviesController
import com.thesidh.zmovie.application.web.routes.RetrieveMoviesRoute
import com.thesidh.zmovie.application.web.routes.SaveMovieRoute
import com.thesidh.zmovie.domain.gateways.storage.MoviesRepository
import com.thesidh.zmovie.domain.services.RetrieveMoviesService
import com.thesidh.zmovie.domain.services.SaveMovieService
import com.thesidh.zmovie.resources.storage.mongodb.MoviesRepositoryImpl
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
    single { MoviesRepositoryImpl(get()) as MoviesRepository }
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