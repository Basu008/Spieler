package com.example.spieler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.LikeRequestBody
import com.example.spieler.repository.Repository
import kotlinx.coroutines.launch

class SingleBlogViewModel(private val repository: Repository): ViewModel() {

    fun likeBlog(likeRequestBody: LikeRequestBody){
        viewModelScope.launch {
            repository.postLike(likeRequestBody)
        }
    }

}