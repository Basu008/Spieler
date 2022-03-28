package com.example.spieler.model

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(

    val status: Int,
    val message: String,
    val content: LoginContentBody
)
