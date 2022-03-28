package com.example.spieler.model

import java.io.Serializable

data class AuthorInfo(
    val _id: String,
    val first_name: String,
    val profile_img: String
): Serializable
