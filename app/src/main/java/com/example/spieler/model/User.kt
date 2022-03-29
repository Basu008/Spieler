package com.example.spieler.model

import java.io.Serializable

data class User(
    val _id: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val profile_img: String
): Serializable
