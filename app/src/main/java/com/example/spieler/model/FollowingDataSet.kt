package com.example.spieler.model

import java.io.Serializable

data class FollowingDataSet(
    val status: Int,
    val message: String,
    val content: ArrayList<FollowData>
): Serializable
