/*
 * *
 *  * Created by Rohan Programmer on 18/02/24, 6:42 pm
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 18/02/24, 6:42 pm
 *
 */

package com.techvista.mvvmtest.api

import com.techvista.mvvmtest.models.ProductListItem
import retrofit2.Response
import retrofit2.http.GET

interface API {

    @GET("/products")
    suspend fun getProducts() : Response<List<ProductListItem>>

}