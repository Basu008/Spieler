package com.example.spieler.api

import com.example.spieler.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BlogApi {
    @GET("blogpages")
    suspend fun getAllBlogs(): Response<BlogResponseBody>

    @POST("blogpage")
    suspend fun postBlog(
        @Body postBlogBody: PostBlogBody
    ): Response<PostBlogResponse>

    @POST("person")
    suspend fun registerUser(
        @Body signUpRequestBody: SignUpRequestBody
    ): Response<SignUpResponseBody>

    @POST("login")
    suspend fun loginUser(
        @Body loginRequestBody: LoginRequestBody
    ): Response<LoginResponseBody>

    @GET("person/{id}")
    suspend fun getCurrentUserDetails(
        @Path("id") id: String
    ): Response<SignUpResponseBody>

    @POST("like")
    suspend fun postLike(
        @Body likeRequestBody: LikeRequestBody
    ): Response<LikeResponseBody>
}