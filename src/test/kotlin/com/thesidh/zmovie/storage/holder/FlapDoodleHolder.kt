package com.thesidh.zmovie.storage.holder

import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network

class FlapDoodleHolder(
    private val host: String,
    private val port: Int
) {
    private lateinit var mongodExecutable: MongodExecutable
    private lateinit var mongodProcess: MongodProcess

    fun start() {
        val starter = MongodStarter.getDefaultInstance()
        val mongodConfig = MongodConfigBuilder()
            .version(Version.Main.V4_0)
            .net(Net(host, port, Network.localhostIsIPv6()))
            .build()
        mongodExecutable = starter.prepare(mongodConfig)
        mongodProcess = mongodExecutable.start()
    }

    fun stop() {
        mongodProcess.stop()
        mongodExecutable.stop()
    }

    companion object {
        fun getInstance() = FlapDoodleHolder(
            host = "localhost",
            port = 27017
        )
    }
}
