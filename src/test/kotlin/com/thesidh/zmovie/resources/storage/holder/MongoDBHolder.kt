package com.thesidh.zmovie.resources.storage.holder

import com.thesidh.zmovie.application.config.mongoDatabase
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import de.flapdoodle.embed.mongo.Command
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.slf4j.LoggerFactory
import java.util.*

class MongoDBHolder(
    private val connectionString: String,
    private val user: String,
    private val password: String,
    private val databaseName: String
) {

    private lateinit var mongodExecutable: MongodExecutable
    lateinit var mongoDatabase: MongoDatabase
    private lateinit var mongodProcess: MongodProcess

    fun start(): MongoDBHolder {

        val logger = LoggerFactory.getLogger("MongoExecutable")

        val runtimeConfig = RuntimeConfigBuilder()
            .defaultsWithLogger(Command.MongoD, logger)
            .build()

        val starter = MongodStarter.getInstance(runtimeConfig)

        val splitConnectionString = connectionString.split(":")
        val hostName = splitConnectionString.first()
        val port = splitConnectionString.last().toInt()

        val mongodConfig = MongodConfigBuilder()
            .version(Version.Main.V3_6)
            .net(Net(hostName, port, Network.localhostIsIPv6()))
            .build()

        mongodExecutable = starter.prepare(mongodConfig)
        mongodProcess = mongodExecutable.start()
        val databaseAdmin = createAppUserAndReturnDatabaseAdmin()
        createCollections(databaseAdmin)
        mongoDatabase = mongoDatabase(connectionString, user, password, databaseName)

        return this
    }

    fun stop() {
        mongodProcess.stop()
        mongodExecutable.stop()
    }

    fun clean() {
        mongoDatabase.listCollectionNames().forEach {
            mongoDatabase.getCollection(it).drop()
        }
    }

    private fun createAppUserAndReturnDatabaseAdmin(): MongoDatabase {
        val databaseAdmin = MongoClients.create("mongodb://$connectionString").getDatabase(databaseName)
        val commandArguments = mapOf(
            "createUser" to user,
            "pwd" to password,
            "roles" to Collections.singletonList(BasicDBObject("role", "readWrite").append("db", databaseName))
        )
        databaseAdmin.runCommand(BasicDBObject(commandArguments))
        return databaseAdmin
    }

    private fun createCollections(databaseAdmin: MongoDatabase) {
        databaseAdmin.createCollection("zmovies")
    }

    companion object {
        fun getInstance() = MongoDBHolder(
            connectionString = "localhost:27017",
            user = "zmovies",
            password = "zmovies",
            databaseName = "zmovies"
        )
    }
}
