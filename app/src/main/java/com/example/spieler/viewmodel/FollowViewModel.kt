package com.example.spieler.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.FollowRequestBody
import com.example.spieler.repository.Repository
import kotlinx.coroutines.launch

class FollowViewModel(private val repository: Repository): ViewModel() {
    fun follow(followRequestBody: FollowRequestBody) {
        viewModelScope.launch {
            repository.followUser(followRequestBody)
        }
    }
}