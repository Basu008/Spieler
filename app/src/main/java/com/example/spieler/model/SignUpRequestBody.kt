package com.example.spieler.model

import com.google.gson.annotations.SerializedName

data class SignUpRequestBody(
    val first_name: String,
    val email: String,
    val password: String,
    @SerializedName("profile_img")
    val profile_img: String,
)
