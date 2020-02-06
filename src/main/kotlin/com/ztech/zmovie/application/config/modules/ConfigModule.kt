package com.ztech.zmovie.application.config.modules

import com.ztech.zmovie.application.config.mongoDatabase
import com.ztech.zmovie.application.web.controllers.SaveMoviesController
import com.ztech.zmovie.application.web.routes.SaveMovieRoute
import com.ztech.zmovie.domain.gateways.storage.MoviesRepository
import com.ztech.zmovie.domain.services.SaveMovieService
import com.ztech.zmovie.resources.storage.mongodb.MoviesRepositoryImpl
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val routesModule: Module = module {
    single { SaveMovieRoute(get()) }
}

val controllersModule: Module = module {
    single { SaveMoviesController(get()) }
}

val serviceModules: Module = module {
    single { SaveMovieService(get()) }
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