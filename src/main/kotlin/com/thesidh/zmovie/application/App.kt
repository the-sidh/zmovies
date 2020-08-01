@file:JvmName("Example")

package com.thesidh.zmovie.application

import com.thesidh.zmovie.application.config.modules.*
import com.thesidh.zmovie.application.web.routes.RetrieveMoviesRoute
import com.thesidh.zmovie.application.web.routes.SaveMovieRoute
import com.thesidh.zmovie.domain.exceptions.APIException
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
    @JvmStatic
    fun main(args: Array<String>) {
        start()

    }
}
