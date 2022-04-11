package com.example.spieler.model

data class FollowResponseBody(
    val status: Int,
    val message: String,
    val content: FollowData
)
