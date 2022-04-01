package com.example.spieler.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spieler.repository.Repository
import com.example.spieler.viewmodel.CommentViewModel

class CommentViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentViewModel(repository) as T
    }
}