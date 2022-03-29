package com.example.spieler.repository

import com.example.spieler.api.*
import com.example.spieler.model.*

import retrofit2.Response

class Repository {

    suspend fun getAllBlogs(): Response<BlogResponseBody>{
        return RetrofitInstance.api.getAllBlogs()
    }

    suspend fun postBlog(postBlogBody: PostBlogBody): Response<PostBlogResponse>{
        return RetrofitInstance.api.postBlog(postBlogBody)
    }

    suspend fun registerUser(signUpRequestBody: SignUpRequestBody): Response<SignUpResponseBody>  {
        return RetrofitInstance.api.registerUser(signUpRequestBody)
    }

    suspend fun loginUser(loginRequestBody: LoginRequestBody): Response<LoginResponseBody>{
        return RetrofitInstance.api.loginUser(loginRequestBody)
    }

    suspend fun getCurrentUserDetails(id: String): Response<SignUpResponseBody>{
        return RetrofitInstance.api.getCurrentUserDetails(id)
    }
}