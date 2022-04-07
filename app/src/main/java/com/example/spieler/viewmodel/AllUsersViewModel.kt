package com.example.spieler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.AllUsers
import com.example.spieler.model.SignUpResponseBody
import com.example.spieler.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class AllUsersViewModel(private val repository: Repository): ViewModel() {
    private val _allUsers = MutableLiveData<Response<AllUsers>>()
    val allUsers: LiveData<Response<AllUsers>>
        get() = _allUsers

    fun getAllUsers(){
        viewModelScope.launch {
            val responseBody = repository.getAllUsers()
            _allUsers.value = responseBody
        }
    }
}