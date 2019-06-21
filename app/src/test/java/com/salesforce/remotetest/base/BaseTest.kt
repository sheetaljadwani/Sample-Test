package com.salesforce.remotetest.base

import com.salesforce.remotetest.di.*
import com.salesforce.remotetest.di.NetModule
import com.salesforce.remotetest.di.RepositoryModule
import com.salesforce.remotetest.util.BASE_URL
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.salesforce.remotetest.di.TestAppComponent
import com.salesforce.remotetest.di.TestRxJavaModule
import org.junit.Before
import java.io.File

abstract class BaseTest {

    lateinit var testAppComponent: TestAppComponent
    lateinit var mockServer: MockWebServer

    @Before
    open fun setUp(){
        this.configureMockServer()
        this.configureDi()
    }

//    @AfterTest
    open fun tearDown(){
        this.stopMockServer()
    }

    open fun configureDi(){
        this.testAppComponent = DaggerTestAppComponent.builder()
            .netModule(NetModule(if (isMockServerEnabled()) mockServer.url("/").toString() else BASE_URL))
            .repositoryModule(RepositoryModule())
            .testRxJavaModule(TestRxJavaModule())
            .build()
        this.testAppComponent.inject(this)
    }

    abstract fun isMockServerEnabled(): Boolean

    open fun configureMockServer(){
        if (isMockServerEnabled()){
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled()){
            mockServer.shutdown()
        }
    }

    open fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    private fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}