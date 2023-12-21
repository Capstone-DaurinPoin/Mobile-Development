package com.daurinpoin.app.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RewardsResponse(
    @SerialName("data") val data: List<DataItem?>? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("status") val status: String? = null
)

@Serializable
data class DataItem(
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("id_reward") val idReward: Int? = null,
    @SerialName("price") val price: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("stock") val stock: Int? = null,
    @SerialName("updatedAt") val updatedAt: String? = null
)

