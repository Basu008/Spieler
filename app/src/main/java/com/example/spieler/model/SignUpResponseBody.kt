package com.example.spieler.model

import com.google.gson.annotations.SerializedName

data class SignUpResponseBody(
    val status: Int,
    val message: String,
    @SerializedName("content")
    val content: User?
)
