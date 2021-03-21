package com.thesidh.zmovie.application.config

import com.thesidh.zmovie.application.messaging.consumer.SaveMovieConsumer
import com.thesidh.zmovie.application.messaging.factory.SQSQueueSessionFactory
import com.thesidh.zmovie.application.messaging.producer.SaveMovieProducer
import com.thesidh.zmovie.application.rest.controllers.*
import com.thesidh.zmovie.domain.services.DeleteMoviesService
import com.thesidh.zmovie.domain.services.RetrieveMovieService
import com.thesidh.zmovie.storage.mongodb.config.mongoDatabase
import com.thesidh.zmovie.domain.storage.MoviesRepository
import com.thesidh.zmovie.domain.services.RetrieveMoviesByRateService
import com.thesidh.zmovie.domain.services.SaveMovieService
import com.thesidh.zmovie.domain.services.UpdateMovieService
import com.thesidh.zmovie.application.rest.routes.*
import com.thesidh.zmovie.storage.mongodb.MongoDBMoviesRepository
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val buildSession = SQSQueueSessionFactory("http://localhost:9324").getSession()
val queue = buildSession.createQueue("movies-queue")

val messagingModule: Module = module{
    single{
        SaveMovieProducer(buildSession = buildSession, queue = queue)
    }
    single{
        SaveMovieConsumer(buildSession = buildSession, queue = queue,service = get())
    }

}

val produceMessageRoutes: Module = module{
    single{ProduceMessageRoute(get())}
}

val produceMessageController: Module = module{
    single{ProduceMessageController(get())}
}
val saveMovieRoutesModule: Module = module {
    single { SaveMovieRoute(get()) }
}

val retrieveMoviesByRateRoutesModule: Module = module {
    single { RetrieveMoviesbyRateRoute(get()) }
}

val retrieveMovieRoutesModule: Module = module {
    single { RetrieveMovieRoute(get()) }
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

val retrieveMoviesByRateControllersModule: Module = module {
    single { RetrieveMoviesByRateController(get()) }
}

val retrieveMovieControllersModule: Module = module {
    single { RetrieveMovieController(get()) }
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

val retrieveMoviesByRateServiceModule: Module = module {
    single { RetrieveMoviesByRateService(get()) }
}

val retrieveMovieServiceModule: Module = module {
    single { RetrieveMovieService(get()) }
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