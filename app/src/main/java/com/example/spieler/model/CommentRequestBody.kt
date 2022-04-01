package com.example.spieler.model

import java.io.Serializable

data class CommentRequestBody(
    val user_id: String,
    val post_id: String,
    val description: String
): Serializable
