package com.daurinpoin.app.service

import com.daurinpoin.app.response.LoginResponse
import com.daurinpoin.app.response.NewsResponse
import com.daurinpoin.app.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("news")
    suspend fun getNews(): NewsResponse
}
