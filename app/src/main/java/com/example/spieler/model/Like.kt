package com.example.spieler.model

import java.io.Serializable

data class Like(
    val _id: String,
    val post_id: String,
    val user_id: String
): Serializable
