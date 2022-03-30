package com.example.spieler.model

import java.io.Serializable

data class LikeResponseBody(
    val status: Int,
    val message: String,
    val content: Like
): Serializable
