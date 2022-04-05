package com.example.spieler.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostBlogBody(
    val title: String,
    val description: String,
    val User_ID: String,
    val blog_img: String,
    val tag: String,
    val category: String = ""
): Serializable
