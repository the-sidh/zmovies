package com.ztech.zmovie.application.config.modules

import com.ztech.zmovie.application.config.mongoDatabase
import com.ztech.zmovie.application.web.controllers.RetrieveMoviesController
import com.ztech.zmovie.application.web.controllers.SaveMoviesController
import com.ztech.zmovie.application.web.routes.RetrieveMoviesRoute
import com.ztech.zmovie.application.web.routes.SaveMovieRoute
import com.ztech.zmovie.domain.gateways.storage.MoviesRepository
import com.ztech.zmovie.domain.services.RetrieveMoviesService
import com.ztech.zmovie.domain.services.SaveMovieService
import com.ztech.zmovie.resources.storage.mongodb.MoviesRepositoryImpl
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