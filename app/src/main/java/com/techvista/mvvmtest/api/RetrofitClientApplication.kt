/*
 * *
 *  * Created by Rohan Programmer on 18/02/24, 6:42 pm
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 18/02/24, 6:42 pm
 *
 */

package com.techvista.mvvmtest.api

import android.app.Application
import com.techvista.mvvmtest.repository.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClientApplication : Application() {

    lateinit var api: API
    lateinit var repository: Repository
    private var retrofit: Retrofit? = null
//     var Base_Url="https://fakestoreapi.com/"
     var Base_Url="https://devclientserver.com/geauxchef/api/v1/chef/"

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Base_Url)
            .build()

        api = retrofit.create(API::class.java)
        repository = Repository(api)
    }

    fun getClient(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("token","token")

                    .build()
                chain.proceed(request)
            }.build()

        retrofit = Retrofit.Builder()
            .baseUrl(Base_Url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

}