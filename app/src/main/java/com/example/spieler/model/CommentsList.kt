package com.example.spieler.model

import java.io.Serializable

data class CommentsList(
    val status: Int,
    val message: String,
    val content: PostCommentBody
): Serializable