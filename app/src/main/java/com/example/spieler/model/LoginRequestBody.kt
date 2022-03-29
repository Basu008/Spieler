package com.example.spieler.model

import java.io.Serializable

data class LoginRequestBody(
    val email: String,
    val password: String
): Serializable
