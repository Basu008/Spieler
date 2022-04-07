package com.example.spieler.model

data class UpdateUserResponse(
    val status: Int,
    val message: String,
    val content: UpdateUser
)