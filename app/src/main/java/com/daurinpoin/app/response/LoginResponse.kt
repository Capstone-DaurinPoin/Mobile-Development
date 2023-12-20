package com.daurinpoin.app.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("data") val data: LoginResult? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("status") val status: String? = null
)

@Serializable
data class LoginResult(
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("nama") val nama: String? = null,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("idUser") val idUser: Int? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("point") val point: Int? = null,
    @SerialName("updatedAt") val updatedAt: String? = null
)


