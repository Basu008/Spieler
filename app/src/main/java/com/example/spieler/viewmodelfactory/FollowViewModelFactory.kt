package com.example.spieler.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spieler.repository.Repository
import com.example.spieler.viewmodel.FollowViewModel

class FollowViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FollowViewModel(repository) as T
    }
}