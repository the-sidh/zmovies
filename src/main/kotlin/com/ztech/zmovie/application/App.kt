package com.ztech.zmovie.application

import com.ztech.zmovie.application.config.modules.*
import com.ztech.zmovie.application.web.routes.SaveMovieRoute
import com.ztech.zmovie.domain.exceptions.APIException
import io.javalin.Javalin
import io.javalin.core.util.HttpResponseExceptionMapper.handleException
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject


object App : KoinComponent {
    private val saveMovieRoute: SaveMovieRoute by inject()
    fun start(extraProperties: Map<String, Any> = emptyMap()) {

        StandAloneContext.startKoin(
            listOf(
                routesModule,
                serviceModules,
                controllersModule,
                repositoryModules,
                mongoModule
            ),
            useEnvironmentProperties = true,
            useKoinPropertiesFile = true,
            extraProperties = extraProperties
        )

        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
            exception(APIException::class.java) { e, ctx -> e.handleError(ctx) }
            error(404) { ctx -> ctx.json("not found") }
        }.start(7000)

        app.routes {
            saveMovieRoute.register()
        }
    }
}

fun main(args: Array<String>) {
    App.start()

}