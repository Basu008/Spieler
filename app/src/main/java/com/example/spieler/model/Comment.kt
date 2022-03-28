package com.example.spieler.model

import java.io.Serializable

data class Comment(
    val _id: String,
    val comment_author: CommentAuthor,
    val description: String
): Serializable
