package com.example.spieler.api

import com.example.spieler.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofitInstance by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: BlogApi by lazy{
        retrofitInstance.create(BlogApi::class.java)
    }
}