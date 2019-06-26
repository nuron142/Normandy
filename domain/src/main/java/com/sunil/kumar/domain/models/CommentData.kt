package com.sunil.kumar.domain.models

import com.google.gson.annotations.SerializedName

data class CommentData(

    @SerializedName("id")
    val id: String,

    @SerializedName("postId")
    val postId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("body")
    val body: String? = null
)