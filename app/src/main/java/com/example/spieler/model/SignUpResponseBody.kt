package com.example.spieler.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpResponseBody(
    val status: Int,
    val message: String,
    @SerializedName("content")
    val content: User?
): Serializable
