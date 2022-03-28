package com.example.spieler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.*
import com.example.spieler.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(val repository: Repository): ViewModel() {

    private val _currentUser = MutableLiveData<Response<LoginResponseBody>>()
    val currentUser: LiveData<Response<LoginResponseBody>>
        get() = _currentUser

    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User>
        get() = _userDetails


    fun loginUser(loginRequestBody: LoginRequestBody){
        viewModelScope.launch {
            val response = repository.loginUser(loginRequestBody)
            _currentUser.value = response
        }
    }

    fun getCurrentUserDetails(id: String){
        viewModelScope.launch {
            val response = repository.getCurrentUserDetails(id)
            if(response.isSuccessful){
                _userDetails.value = response.body()?.content!!
            }
        }
    }

}