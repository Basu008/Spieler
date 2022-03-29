package com.example.spieler.model

import java.io.Serializable

data class PostBlogResponseContent(
    val _id: String,
    val title: String,
    val description: String,
    val user_id: String,
    val blog_img: String,
    val time: String
): Serializable