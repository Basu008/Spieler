package com.example.spieler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.BlogResponseBody
import com.example.spieler.model.FollowingDataSet
import com.example.spieler.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: Repository): ViewModel(){
    private val _allBlogs = MutableLiveData<Response<BlogResponseBody>>()
    val allBlogs: LiveData<Response<BlogResponseBody>>
        get() = _allBlogs

    private val _allFollowers = MutableLiveData<Response<FollowingDataSet>>()
    val allFollowers: LiveData<Response<FollowingDataSet>>
        get() = _allFollowers

    fun getAllBlogs(){
        viewModelScope.launch {
            val response = repository.getAllBlogs()
            _allBlogs.value = response
        }
    }

    fun getAllFollowers(){
        viewModelScope.launch {
            val response = repository.getAllFollowers()
            _allFollowers.value = response
        }
    }

    fun getCurrentUser(id: String){

    }
}
