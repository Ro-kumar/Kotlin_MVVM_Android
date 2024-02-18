/*
 * *
 *  * Created by Rohan Programmer on 18/02/24, 6:42 pm
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 18/02/24, 6:42 pm
 *
 */

package com.techvista.mvvmtest.repository

import com.techvista.mvvmtest.api.API
import com.techvista.mvvmtest.models.ProductListItem
import com.techvista.mvvmtest.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RepositoryTest {

    @Mock
    lateinit var API: API

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetProducts_EmptyList() = runTest {
        Mockito.`when`(API.getProducts()).thenReturn(Response.success(emptyList()))

        val sut = Repository(API)
        val result = sut.getProducts()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(0, result.data!!.size)
    }

    @Test
    fun testGetProducts_expectedProductList() = runTest {
        val productList = listOf<ProductListItem>(
            ProductListItem("", "", 1, "", 40.3, "Prod 1"),
            ProductListItem("", "", 2, "", 20.2, "Prod 2")
        )
        Mockito.`when`(API.getProducts()).thenReturn(Response.success(productList))

        val sut = Repository(API)
        val result = sut.getProducts()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(2, result.data!!.size)
        Assert.assertEquals("Prod 1", result.data!![0].title)
    }

    @Test
    fun testGetProducts_expectedError() = runTest {
        Mockito.`when`(API.getProducts()).thenReturn(Response.error(401, "Unauthorized".toResponseBody()))

        val sut = Repository(API)
        val result = sut.getProducts()
        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("Something went wrong", result.message)
    }
}