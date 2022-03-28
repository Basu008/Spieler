package com.example.spieler.model

data class Blog(
    val _id: String,
    val author_info: AuthorInfo,
    val blog_img: String,
    val comment: List<Comment>,
    val created_at: String,
    val description: String,
    val likes: List<Like>,
    val title: String
)
