package com.thesidh.zmovie.storage.extension

import com.mongodb.client.MongoDatabase
import com.thesidh.zmovie.storage.holder.MongoDBHolder
import org.junit.jupiter.api.extension.*

class MongoDBExtension : ParameterResolver, BeforeAllCallback, AfterAllCallback, BeforeEachCallback {
    private val mongoDatabaseHolder: MongoDBHolder = MongoDBHolder.getInstance()

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext?.parameter?.type?.isAssignableFrom(MongoDatabase::class.java) ?: false
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        return mongoDatabaseHolder.mongoDatabase
    }

    override fun beforeAll(context: ExtensionContext?) {
        mongoDatabaseHolder.start()
    }

    override fun afterAll(context: ExtensionContext?) {
        mongoDatabaseHolder.stop()
    }

    override fun beforeEach(context: ExtensionContext?) {
        mongoDatabaseHolder.clean()
    }
}
