package com.example.spieler.api

import com.example.spieler.model.*
import retrofit2.Response
import retrofit2.http.*

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

    @HTTP(method = "DELETE", path = "/like/dlt/{id}", hasBody = true)
    suspend fun deleteLike(
        @Path("id") id: String,
        @Body deleteLikeRequestBody: DeleteLikeRequestBody
    ): Response<DeleteLikeResponseBody>

    @POST("comment")
    suspend fun postComment(
        @Body commentRequestBody: CommentRequestBody
    ): Response<CommentResponseBody>

    @PUT("person/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body updateUser: UpdateUser
    ): Response<UpdateUserResponse>

    @GET("person")
    suspend fun getAllUsers(): Response<AllUsers>

    @POST("follower")
    suspend fun followUser(
        @Body followRequestBody: FollowRequestBody
    ): Response<FollowResponseBody>

    @GET("followers")
    suspend fun getAllFollowers(): Response<FollowingDataSet>

    @HTTP(method = "DELETE", path = "/followers/dlt/{id}", hasBody = true)
    suspend fun unfollowUser(
        @Path("id") id: String,
        @Body unfollowRequestBody: UnfollowRequestBody
    )
}