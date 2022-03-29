package com.example.spieler.model

import java.io.Serializable

data class PostBlogResponse(
    val status: Int,
    val message: String,
    val content: PostBlogResponseContent
): Serializable
