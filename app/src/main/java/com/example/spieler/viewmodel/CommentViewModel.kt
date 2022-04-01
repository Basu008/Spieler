package com.example.spieler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.CommentRequestBody
import com.example.spieler.model.CommentResponseBody
import com.example.spieler.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class CommentViewModel(private val repository: Repository): ViewModel(){

    private val _commentResponse = MutableLiveData<Response<CommentResponseBody>>()
    val commentResponse: LiveData<Response<CommentResponseBody>>
        get() = _commentResponse



    fun postComment(commentRequestBody: CommentRequestBody){
        viewModelScope.launch {
            val response = repository.postComment(commentRequestBody)
            _commentResponse.value = response
        }
    }

}
