package com.yuch.githubuserapp.data.api

import com.yuch.githubuserapp.BuildConfig
import com.yuch.githubuserapp.data.response.DataUser
import com.yuch.githubuserapp.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
        const val API_KEY = BuildConfig.API_KEY
    }
    @Headers("Authorization: $API_KEY")
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ):Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DataUser>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<DataUser>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<DataUser>>
}
