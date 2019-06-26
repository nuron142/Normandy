package com.sunil.kumar.domain.models

import com.google.gson.annotations.SerializedName

data class UserData(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("username")
    val username: String? = null,

    @SerializedName("email")
    val email: String? = null
) {

    fun isInvalid(): Boolean {
        return id.isEmpty()
    }
}
