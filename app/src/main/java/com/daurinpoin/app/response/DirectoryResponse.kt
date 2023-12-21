package com.daurinpoin.app.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectoryResponse(
    @SerialName("data")
    val data: List<DirectoryResult>? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class DirectoryResult(
    @SerialName("createdAt")
    val createdAt: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("directory_id")
    val directoryId: Int? = null,

    @SerialName("updatedAt")
    val updatedAt: String? = null
)

