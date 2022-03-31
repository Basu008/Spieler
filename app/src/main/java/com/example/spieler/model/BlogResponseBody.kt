package com.example.spieler.model

import java.io.Serializable

data class BlogResponseBody(
    val status: Int,
    val message: String,
    val content: ArrayList<Blog>
): Serializable
