package com.thesidh.zmovie.storage.mongodb.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.codecs.ValueCodecProvider
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider

fun mongoClientConnection(
    connectionString: String,
    credential: MongoCredential,
    codecRegistries: CodecRegistry
): MongoClient {
    return MongoClients.create(
        MongoClientSettings.builder()
            .applyConnectionString(ConnectionString("mongodb://$connectionString"))
            .credential(credential)
            .codecRegistry(codecRegistries)
            .build()
    )
}

fun mongoDatabase(
    connectionString: String,
    user: String,
    password: String,
    databaseName: String
): MongoDatabase {
    val codecRegistries = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true).build(),
            ValueCodecProvider()
        )
    )
    return mongoClientConnection(
        connectionString,
        getMongoCredentials(user, password, databaseName),
        codecRegistries
    )
        .getDatabase(databaseName)
}

private fun getMongoCredentials(
    user: String,
    password: String,
    databaseName: String
) = MongoCredential.createCredential(
    user,
    databaseName,
    password.toCharArray()
)
