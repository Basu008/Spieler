package com.example.spieler.model

import java.io.Serializable

data class PostCommentBody(
    val _id: String,
    val description: String,
    val User_ID: String,
    val Post_ID: String
): Serializable
