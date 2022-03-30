package com.example.spieler.model

data class BlogResponseBody(
    val status: Int,
    val message: String,
    val content: ArrayList<Blog>
)
