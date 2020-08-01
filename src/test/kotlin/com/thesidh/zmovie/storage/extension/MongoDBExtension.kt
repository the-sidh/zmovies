package com.thesidh.zmovie.storage.extension

import com.mongodb.client.MongoDatabase
import com.thesidh.zmovie.storage.holder.FlapDoodleHolder
import com.thesidh.zmovie.storage.holder.MongoDBHolder
import org.junit.jupiter.api.extension.*

class MongoDBExtension : ParameterResolver, BeforeAllCallback, AfterAllCallback, BeforeEachCallback {
    private val flapDoodleHolder: FlapDoodleHolder = FlapDoodleHolder.getInstance()
    private val mongoDBHolder: MongoDBHolder = MongoDBHolder.getInstance()

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext?.parameter?.type?.isAssignableFrom(MongoDatabase::class.java) ?: false
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        return mongoDBHolder.mongoDatabase
    }

    override fun beforeAll(context: ExtensionContext?) {
        flapDoodleHolder.start()
        mongoDBHolder.start()
    }

    override fun afterAll(context: ExtensionContext?) {
        flapDoodleHolder.stop()
    }

    override fun beforeEach(context: ExtensionContext?) {
        mongoDBHolder.clean()
    }
}
