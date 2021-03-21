package com.thesidh.zmovie.application.messaging.factory

import com.amazon.sqs.javamessaging.ProviderConfiguration
import com.amazon.sqs.javamessaging.SQSConnectionFactory
import com.amazon.sqs.javamessaging.SQSSession
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import javax.jms.Connection
import javax.jms.Session

const val MESSAGE_BROKER_REGION = "elasticmq"
const val MESSAGE_BROKER_ACCESS_KEY = "x"
const val MESSAGE_BROKER_SECRET_KEY = "x"

class SQSQueueSessionFactory(private val endpoint: String) {

    private val connection: Connection
    private val client: AmazonSQS

    init {
        client = AmazonSQSClientBuilder.standard()
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(
                    endpoint,
                    MESSAGE_BROKER_REGION
                )
            ).apply {
                // Just use for test, unfortunately.
                if ("" != null)
                    this.withCredentials(
                        AWSStaticCredentialsProvider(
                            BasicAWSCredentials(
                                MESSAGE_BROKER_ACCESS_KEY,
                                MESSAGE_BROKER_SECRET_KEY
                            )
                        )
                    )
            }!!.build()
        val sqsConnectionFactory = SQSConnectionFactory(ProviderConfiguration(), client)
        connection = sqsConnectionFactory.createConnection()
        connection.start()
    }

    fun getClient() = client

    fun stop() {
        connection.stop()
    }

    fun getSession(): Session = connection.createSession(false, SQSSession.UNORDERED_ACKNOWLEDGE)
}
