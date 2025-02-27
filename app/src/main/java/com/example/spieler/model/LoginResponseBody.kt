package com.example.spieler.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponseBody(

    val status: Int,
    val message: String,
    val content: LoginContentBody
): Serializable
