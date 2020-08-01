package com.thesidh.zmovie.storage.holder

import com.thesidh.zmovie.storage.mongodb.config.mongoDatabase
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import java.util.*

class MongoDBHolder(
    private val host: String,
    private val port: Int,
    private val user: String,
    private val password: String,
    private val databaseName: String
) {
    private lateinit var mongodExecutable: MongodExecutable
    lateinit var mongoDatabase: MongoDatabase
    private lateinit var mongodProcess: MongodProcess

    fun start(): MongoDBHolder {
        val starter = MongodStarter.getDefaultInstance()
        val mongodConfig = MongodConfigBuilder()
            .version(Version.Main.V4_0)
            .net(Net(host, port, Network.localhostIsIPv6()))
            .build()

        mongodExecutable = starter.prepare(mongodConfig)
        mongodProcess = mongodExecutable.start()
        createCollections()
        mongoDatabase =
            mongoDatabase(
                "$host:$port",
                user,
                password,
                databaseName
            )
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

    private fun createCollections() {
        val databaseAdmin = createDBAdmin()
        databaseAdmin.createCollection("zmovies")
    }

    private fun createDBAdmin(): MongoDatabase {
        val databaseAdmin = MongoClients.create("mongodb://$host:$port").getDatabase(databaseName)
        val commandArguments = mapOf(
            "createUser" to user,
            "pwd" to password,
            "roles" to Collections.singletonList(BasicDBObject("role", "readWrite").append("db", databaseName))
        )
        databaseAdmin.runCommand(BasicDBObject(commandArguments))
        return databaseAdmin
    }

    companion object {
        fun getInstance() = MongoDBHolder(
            host = "localhost",
            port = 27017,
            user = "zmovies",
            password = "zmovies",
            databaseName = "zmovies"
        )
    }
}
