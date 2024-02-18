/*
 * *
 *  * Created by Rohan Programmer on 18/02/24, 6:42 pm
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 18/02/24, 6:42 pm
 *
 */

package com.techvista.mvvmtest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.techvista.mvvmtest.getOrAwaitValue
import com.techvista.mvvmtest.repository.Repository
import com.techvista.mvvmtest.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_GetProducts() = runTest{
        Mockito.`when`(repository.getProducts()).thenReturn(NetworkResult.Success(emptyList()))

        val sut = MainViewModel(repository)
        sut.getProducts()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.products.getOrAwaitValue()
        Assert.assertEquals(0, result.data!!.size)
    }

    @Test
    fun test_GetProduct_expectedError() = runTest{
        Mockito.`when`(repository.getProducts()).thenReturn(NetworkResult.Error("Something Went Wrong"))

        val sut = MainViewModel(repository)
        sut.getProducts()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.products.getOrAwaitValue()
        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("Something Went Wrong", result.message)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}












