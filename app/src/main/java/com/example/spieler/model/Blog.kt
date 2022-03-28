package com.example.spieler.model

data class Blog(
    val _id: String,
    val blog_img: String,
    val blog_likes: List<Like>,
    val comment: List<Comment>,
    val description: String,
    val title: String
)
