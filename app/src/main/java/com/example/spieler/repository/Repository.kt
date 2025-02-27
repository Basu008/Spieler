package com.example.spieler.repository

import com.example.spieler.api.*
import com.example.spieler.model.*

import retrofit2.Response

class Repository {

    suspend fun getAllBlogs(): Response<BlogResponseBody>{
        return RetrofitInstance.api.getAllBlogs()
    }

    suspend fun getAllUsers(): Response<AllUsers>{
        return RetrofitInstance.api.getAllUsers()
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

    suspend fun postLike(likeRequestBody: LikeRequestBody): Response<LikeResponseBody>{
        return RetrofitInstance.api.postLike(likeRequestBody)
    }

    suspend fun deleteLike(id: String, deleteLikeRequestBody: DeleteLikeRequestBody): Response<DeleteLikeResponseBody>{
        return RetrofitInstance.api.deleteLike(id, deleteLikeRequestBody)
    }

    suspend fun postComment(commentRequestBody: CommentRequestBody) : Response<CommentResponseBody>{
        return RetrofitInstance.api.postComment(commentRequestBody)
    }

    suspend fun updateUser(id: String, updateUser: UpdateUser): Response<UpdateUserResponse>{
        return RetrofitInstance.api.updateUser(id,updateUser)
    }

    suspend fun followUser(followRequestBody: FollowRequestBody): Response<FollowResponseBody>{
        return RetrofitInstance.api.followUser(followRequestBody)
    }

    suspend fun getAllFollowers(): Response<FollowingDataSet>{
        return RetrofitInstance.api.getAllFollowers()
    }

    suspend fun unfollowUser(id: String, unfollowRequestBody: UnfollowRequestBody){
        RetrofitInstance.api.unfollowUser(id, unfollowRequestBody)
    }
}