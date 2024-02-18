/*
 * *
 *  * Created by Rohan Programmer on 18/02/24, 6:42 pm
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 18/02/24, 6:42 pm
 *
 */

package com.techvista.mvvmtest.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techvista.mvvmtest.R


import com.techvista.mvvmtest.api.RetrofitClientApplication
import com.techvista.mvvmtest.adapter.ProductAdapter
import com.techvista.mvvmtest.utils.NetworkResult
import com.techvista.mvvmtest.viewmodels.MainViewModel
import com.techvista.mvvmtest.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.productList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val repository = (application as RetrofitClientApplication).repository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        mainViewModel.getProducts()

        mainViewModel.products.observe(this, Observer {
            when(it){
                is NetworkResult.Success -> {
                    Log.d("CHEEZ", it.data.toString())
                    adapter = ProductAdapter(it.data!!)
                    recyclerView.adapter = adapter

                }
                is NetworkResult.Error -> {    Log.d("CHEEZ", it.message.toString())}
                else -> {
                    Toast.makeText(application, ""+it.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}