/*
 * *
 *  * Created by Rohan Programmer on 18/02/24, 6:42 pm
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 18/02/24, 6:42 pm
 *
 */

package com.techvista.mvvmtest

import com.techvista.mvvmtest.api.API
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APITest {

    lateinit var mockWebServer: MockWebServer
    lateinit var API: API

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        API = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(API::class.java)
    }

    @Test
    fun testGetProducts() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = API.getProducts()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body()!!.isEmpty())
    }

    @Test
    fun testGetProducts_returnProducts() = runTest{
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = API.getProducts()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body()!!.isEmpty())
        Assert.assertEquals(2, response.body()!!.size)
    }

    @Test
    fun testGetProducts_returnError() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong")
        mockWebServer.enqueue(mockResponse)

        val response = API.getProducts()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}












