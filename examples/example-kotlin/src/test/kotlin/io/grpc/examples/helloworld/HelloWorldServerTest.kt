/*
 * Copyright 2016 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.examples.helloworld

import org.junit.Assert.assertEquals

import io.grpc.examples.helloworld.HelloWorldServer.GreeterImpl
import io.grpc.testing.GrpcServerRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Unit tests for [HelloWorldServer].
 * For demonstrating how to write gRPC unit test only.
 * Not intended to provide a high code coverage or to test every major usecase.
 *
 *
 * For more unit test examples see [io.grpc.examples.routeguide.RouteGuideClientTest] and
 * [io.grpc.examples.routeguide.RouteGuideServerTest].
 */
@RunWith(JUnit4::class)
class HelloWorldServerTest {
    /**
     * This creates and starts an in-process server, and creates a client with an in-process channel.
     * When the test is done, it also shuts down the in-process client and server.
     */
    @get:Rule
    val grpcServerRule = GrpcServerRule().directExecutor()

    /**
     * To test the server, make calls with a real stub using the in-process channel, and verify
     * behaviors or state changes from the client side.
     */
    @Test
    @Throws(Exception::class)
    fun greeterImpl_replyMessage() {
        // Add the service to the in-process server.
        grpcServerRule.serviceRegistry.addService(GreeterImpl())

        val blockingStub = GreeterGrpc.newBlockingStub(grpcServerRule.channel)
        val testName = "test name"

        val reply = blockingStub.sayHello(HelloRequest.newBuilder().setName(testName).build())

        assertEquals("Hello $testName", reply.message)
    }
}