package com.ztech.zmovie.application

import com.ztech.zmovie.application.config.modules.*
import com.ztech.zmovie.application.web.routes.RetrieveMoviesRoute
import com.ztech.zmovie.application.web.routes.SaveMovieRoute
import com.ztech.zmovie.domain.exceptions.APIException
import io.javalin.Javalin
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject


object App : KoinComponent {
    private lateinit var app: Javalin
    private val saveMovieRoute: SaveMovieRoute by inject()
    private val retrieveMoviesRoute: RetrieveMoviesRoute by inject()
    fun start(extraProperties: Map<String, Any> = emptyMap()) {

        StandAloneContext.startKoin(
            listOf(
                saveMovieRoutesModule,
                saveMovieServiceModule,
                saveMovieControllersModule,
                retrieveMoviesControllersModule,
                retrieveMoviesServiceModule,
                retrieveMoviesRoutesModule,
                repositoryModules,
                mongoModule
            ),
            useEnvironmentProperties = true,
            useKoinPropertiesFile = true,
            extraProperties = extraProperties
        )

        app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            exception(APIException::class.java) { e, ctx -> e.handleError(ctx) }
            error(404) { ctx -> ctx.json("not found") }
        }.start(7000)

        app.routes {
            saveMovieRoute.register()
            retrieveMoviesRoute.register()
        }
    }

    fun shutdown(){
        app.stop()
        StandAloneContext.stopKoin()
    }
}

fun main(args: Array<String>) {
    App.start()

}