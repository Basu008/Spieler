package com.example.spieler.model

import java.io.Serializable

data class FollowRequestBody(
    val user_id: String,
    val following_id: String
): Serializable