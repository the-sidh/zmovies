package com.thesidh.zmovie.rest.config

import com.thesidh.zmovie.domain.services.DeleteMoviesService
import com.thesidh.zmovie.storage.mongodb.config.mongoDatabase
import com.thesidh.zmovie.rest.controllers.RetrieveMoviesController
import com.thesidh.zmovie.rest.controllers.SaveMoviesController
import com.thesidh.zmovie.rest.routes.RetrieveMoviesRoute
import com.thesidh.zmovie.rest.routes.SaveMovieRoute
import com.thesidh.zmovie.domain.storage.MoviesRepository
import com.thesidh.zmovie.domain.services.RetrieveMoviesService
import com.thesidh.zmovie.domain.services.SaveMovieService
import com.thesidh.zmovie.domain.services.UpdateMovieService
import com.thesidh.zmovie.rest.controllers.DeleteMoviesController
import com.thesidh.zmovie.rest.controllers.UpdateMovieController
import com.thesidh.zmovie.rest.routes.DeleteMovieRoute
import com.thesidh.zmovie.rest.routes.UpdateMovieRoute
import com.thesidh.zmovie.storage.mongodb.MongoDBMoviesRepository
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val saveMovieRoutesModule: Module = module {
    single { SaveMovieRoute(get()) }
}

val retrieveMoviesRoutesModule: Module = module {
    single { RetrieveMoviesRoute(get()) }
}

val deleteMoviesRoutesModule: Module= module {
    single { DeleteMovieRoute(get()) }
}

val updateMovieRoutesModule: Module= module {
    single { UpdateMovieRoute(get()) }
}

val saveMovieControllersModule: Module = module {
    single { SaveMoviesController(get()) }
}

val retrieveMoviesControllersModule: Module = module {
    single { RetrieveMoviesController(get()) }
}

val deleteMoviesControllersModule: Module = module {
    single { DeleteMoviesController(get()) }
}

val updateMovieControllersModule: Module = module {
    single { UpdateMovieController(get()) }
}

val saveMovieServiceModule: Module = module {
    single { SaveMovieService(get()) }
}

val retrieveMoviesServiceModule: Module = module {
    single { RetrieveMoviesService(get()) }
}

val deleteMoviesServiceModule: Module = module {
    single { DeleteMoviesService(get()) }
}

val updateMoviesServiceModule: Module = module {
    single { UpdateMovieService(get()) }
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