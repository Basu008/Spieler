package com.example.spieler.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginContentBody(
    val token: String,
    val user_id: String
) : Serializable
