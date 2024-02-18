package com.techvista.mvvmtest.models.Login

data class loginResponse(
    val code: Int,
    val `data`: Data,
    val isLogged: Boolean,
    val message: String,
    val status: String,
    val token: String
)