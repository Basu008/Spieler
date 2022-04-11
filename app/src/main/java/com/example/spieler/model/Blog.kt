package com.example.spieler.model

import java.io.Serializable

data class Blog(
    val _id: String,
    val author_info: AuthorInfo,
    val blog_img: String?,
    val comment: ArrayList<Comment>,
    val time: String,
    val description: String,
    val tag: String,
    val category: String,
    val likes: ArrayList<Like>,
    val title: String
): Serializable
