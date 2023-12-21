package com.daurinpoin.app.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(

    @SerialName("data")
    val data: List<DataItem?>? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class CartResult(

    @SerialName("shop_id")
    val shopId: Int? = null,

    @SerialName("createdAt")
    val createdAt: String? = null,

    @SerialName("id_reward")
    val idReward: Int? = null,

    @SerialName("jumlahProduct")
    val jumlahProduct: Int? = null,

    @SerialName("id_user")
    val idUser: Int? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("updatedAt")
    val updatedAt: String? = null
)

