package com.example.spieler.model

import java.io.Serializable

data class UnfollowRequestBody(
    val following_id: String
): Serializable