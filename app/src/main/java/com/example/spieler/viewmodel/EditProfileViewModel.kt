package com.example.spieler.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.*
import com.example.spieler.repository.Repository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class EditProfileViewModel(private val repository: Repository): ViewModel() {
    private val userProfilePicRef = Firebase.storage.reference

    private val _editUserResponse = MutableLiveData<Response<UpdateUserResponse>>()
    val editUserResponse: LiveData<Response<UpdateUserResponse>>
        get() = _editUserResponse

    private val _userUpdated = MutableLiveData<Boolean>()
    val userUpdated: LiveData<Boolean>
        get() = _userUpdated

    private val _getUpdatedUser = MutableLiveData<Response<SignUpResponseBody>>()
    val getUpdatedUser: LiveData<Response<SignUpResponseBody>>
        get() = _getUpdatedUser

    private val _allFollowers = MutableLiveData<Response<FollowingDataSet>>()
    val allFollowers: LiveData<Response<FollowingDataSet>>
        get() = _allFollowers


    fun getAllFollowers(){
        viewModelScope.launch {
            val response = repository.getAllFollowers()
            _allFollowers.value = response
        }
    }

    fun uploadImageToFirebase(uri: Uri?, fileName: String
                              , firstName: String,
                              email: String,
                            id: String) = CoroutineScope(Dispatchers.IO).launch{
        try{
            uri?.let {
                val filePath = userProfilePicRef.child("profilePics/$fileName")
                filePath.putFile(it)
                    .addOnSuccessListener {
                        filePath.downloadUrl.addOnSuccessListener { url ->
                            val editUser = UpdateUser(firstName,
                                email,
                                url.toString())
                            editUser(id, editUser)
                        }
                    }
            }?.await()
            withContext(Dispatchers.Main){

            }
        }catch (e: Exception){

        }
    }

    fun editUser(id: String, updateUser: UpdateUser){
        viewModelScope.launch {
            val response = repository.updateUser(id, updateUser)
            _editUserResponse.value = response
            val getUpdatedUserResponse = repository.getCurrentUserDetails(id)
            _getUpdatedUser.value =  getUpdatedUserResponse
        }
    }

}