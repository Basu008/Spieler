package com.example.spieler.model

import java.io.Serializable

data class CommentResponseBody(
    val status: Int,
    val message: String,
    val content: PostCommentBody
): Serializable
