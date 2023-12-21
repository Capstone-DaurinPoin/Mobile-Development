package com.daurinpoin.app.service

import com.daurinpoin.app.response.CartResponse
import com.daurinpoin.app.response.CartResult
import com.daurinpoin.app.response.DirectoryResponse
import com.daurinpoin.app.response.LoginResponse
import com.daurinpoin.app.response.NewsResponse
import com.daurinpoin.app.response.RegisterResponse
import com.daurinpoin.app.response.RewardsResponse
import com.daurinpoin.app.response.UserResponse
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

    @GET("users/{id_user}")
    suspend fun getUser(@Path("id_user") userId: String): UserResponse

    @GET("news")
    suspend fun getNews(): NewsResponse

    @GET("rewards")
    suspend fun getRewards(): RewardsResponse

    @GET("directories/{1}")
    suspend fun getDirectories(): DirectoryResponse

    @POST("shops")
    suspend fun addToCart(@Body cartItem: CartResult): CartResponse
}
