package com.example.spieler.model

import java.io.Serializable

data class FollowData(
    val _id: String,
    val user_id: String,
    val following_id: String
): Serializable
