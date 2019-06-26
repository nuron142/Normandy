package com.sunil.kumar.domain.models

import com.google.gson.annotations.SerializedName

data class PostData(

    @SerializedName("id")
    val id: String,

    @SerializedName("userId")
    val userId: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("body")
    val body: String? = null
)
