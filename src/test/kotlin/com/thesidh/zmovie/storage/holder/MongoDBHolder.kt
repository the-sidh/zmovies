package com.thesidh.zmovie.storage.holder

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.thesidh.zmovie.storage.mongodb.config.mongoDatabase
import java.util.*

class MongoDBHolder(
    private val host: String,
    private val port: Int,
    private val user: String,
    private val password: String,
    private val databaseName: String
) {
    lateinit var mongoDatabase: MongoDatabase
    fun start() {
        mongoDatabase =
            mongoDatabase(
                "$host:$port",
                user,
                password,
                databaseName
            )
        createCollections()
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