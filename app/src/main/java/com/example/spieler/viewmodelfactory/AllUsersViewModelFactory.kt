package com.example.spieler.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spieler.repository.Repository
import com.example.spieler.viewmodel.AllUsersViewModel

class AllUsersViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllUsersViewModel(repository) as T
    }
}