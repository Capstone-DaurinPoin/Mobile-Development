package com.daurinpoin.app.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    @SerialName("registerResult") val data: RegisterResult? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("status") val status: String? = null
)

@Serializable
data class RegisterResult(
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("point") val point: Int? = null
)

