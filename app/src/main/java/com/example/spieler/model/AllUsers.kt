package com.example.spieler.model

data class AllUsers(
    val status: Int,
    val message: String,
    val content: ArrayList<User>
)
