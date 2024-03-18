package com.yuch.githubuserapp.data.api

import com.yuch.githubuserapp.data.api.Api.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiInstance: Api? = retrofit.create(Api::class.java)
}