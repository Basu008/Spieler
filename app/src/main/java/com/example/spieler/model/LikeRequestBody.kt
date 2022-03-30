package com.example.spieler.model

import java.io.Serializable

data class LikeRequestBody(
    val user_id: String,
    val post_id: String
): Serializable