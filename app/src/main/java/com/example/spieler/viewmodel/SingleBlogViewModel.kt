package com.example.spieler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.DeleteLikeRequestBody
import com.example.spieler.model.DeleteLikeResponseBody
import com.example.spieler.model.LikeRequestBody
import com.example.spieler.model.LikeResponseBody
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
}