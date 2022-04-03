package com.example.spieler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.*
import com.example.spieler.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class SingleBlogViewModel(private val repository: Repository): ViewModel() {

    private val _postLiked = MutableLiveData<Response<LikeResponseBody>>()
    val postLiked: LiveData<Response<LikeResponseBody>>
        get() = _postLiked

    private val _postDisliked = MutableLiveData<Response<DeleteLikeResponseBody>>()
    val postDisliked: LiveData<Response<DeleteLikeResponseBody>>
        get() = _postDisliked


    private val _updatedBlog = MutableLiveData<Blog>()
    val updatedBlog: LiveData<Blog>
        get() = _updatedBlog

    fun likeBlog(likeRequestBody: LikeRequestBody){
        viewModelScope.launch {
            val responseBody = repository.postLike(likeRequestBody)
            _postLiked.value = responseBody
        }
    }

    fun dislikeBlog(id: String, deleteLikeRequestBody: DeleteLikeRequestBody){
        viewModelScope.launch {
            val responseBody = repository.deleteLike(id, deleteLikeRequestBody)
            _postDisliked.value = responseBody
        }
    }

    fun updateBlog(blogId: String){
        viewModelScope.launch {
            val response = repository.getAllBlogs()
            if(response.isSuccessful){
                response.body()?.content?.forEach {
                    if(it._id == blogId)
                        _updatedBlog.value = it
                }
            }
        }
    }
}