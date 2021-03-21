@file:JvmName("Example")

package com.thesidh.zmovie.rest

import com.thesidh.zmovie.domain.exceptions.*
import com.thesidh.zmovie.rest.routes.RetrieveMoviesRoute
import com.thesidh.zmovie.rest.routes.SaveMovieRoute
import com.thesidh.zmovie.rest.config.*
import com.thesidh.zmovie.rest.exceptions.InvalidBodySuppliedException
import com.thesidh.zmovie.rest.routes.DeleteMovieRoute
import com.thesidh.zmovie.rest.routes.UpdateMovieRoute
import io.javalin.Context
import io.javalin.Javalin
import org.eclipse.jetty.http.HttpStatus
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject


object App : KoinComponent {
    private lateinit var app: Javalin
    private val saveMovieRoute: SaveMovieRoute by inject()
    private val retrieveMoviesRoute: RetrieveMoviesRoute by inject()
    private val deleteMoviesRoute: DeleteMovieRoute by inject()
    private val updateMovieRoute: UpdateMovieRoute by inject()
    fun start(extraProperties: Map<String, Any> = emptyMap()) {

        StandAloneContext.startKoin(
                listOf(
                        saveMovieRoutesModule,
                        saveMovieServiceModule,
                        saveMovieControllersModule,
                        retrieveMoviesControllersModule,
                        retrieveMoviesServiceModule,
                        retrieveMoviesRoutesModule,
                        deleteMoviesRoutesModule,
                        deleteMoviesServiceModule,
                        deleteMoviesControllersModule,
                        updateMovieRoutesModule,
                        updateMovieControllersModule,
                        updateMoviesServiceModule,
                        repositoryModules,
                        mongoModule
                ),
                useEnvironmentProperties = true,
                useKoinPropertiesFile = true,
                extraProperties = extraProperties
        )

        app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            exception(DatabaseInsertionException::class.java) { e, ctx ->
                handleError(
                        ctx = ctx,
                        e = e,
                        errorStatus = HttpStatus.BAD_GATEWAY_502
                )
            }
            exception(DuplicateMovieTitleException::class.java) { e, ctx ->
                handleError(
                        ctx = ctx,
                        e = e,
                        errorStatus = HttpStatus.CONFLICT_409
                )
            }
            exception(InvalidBodySuppliedException::class.java) { e, ctx ->
                handleError(
                        ctx = ctx,
                        e = e,
                        errorStatus = HttpStatus.UNPROCESSABLE_ENTITY_422
                )
            }
            exception(TooMuchActorsException::class.java) { e, ctx ->
                handleError(
                        ctx = ctx,
                        e = e,
                        errorStatus = HttpStatus.BAD_REQUEST_400
                )
            }
            exception(MovieNotFoundException::class.java) { e, ctx ->
                handleError(
                        ctx = ctx,
                        e = e,
                        errorStatus = HttpStatus.NOT_FOUND_404
                )
            }
            error(404) { ctx -> ctx.json("not found") }
        }.start(7000)

        app.routes {
            saveMovieRoute.register()
            retrieveMoviesRoute.register()
            deleteMoviesRoute.register()
            updateMovieRoute.register()
        }
    }

    private fun handleError(ctx: Context, e: ZMovieException, errorStatus: Int) {
        ctx.json(Error(e.message())).status(errorStatus)
    }

    fun shutdown() {
        app.stop()
        StandAloneContext.stopKoin()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        start()

    }
}
