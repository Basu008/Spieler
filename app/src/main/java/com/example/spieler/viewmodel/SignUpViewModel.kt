package com.example.spieler.viewmodel

import android.net.Uri
import android.widget.Toast
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

class SignUpViewModel(private val repository: Repository): ViewModel() {
    private val userProfilePicRef = Firebase.storage.reference

    private val _currentUser = MutableLiveData<Response<SignUpResponseBody>>()
    val currentUser: LiveData<Response<SignUpResponseBody>>
        get() = _currentUser
    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean>
        get() = _uploadStatus

    fun registerUser(signUpRequestBody: SignUpRequestBody) {
        viewModelScope.launch {
            _currentUser.value = repository.registerUser(signUpRequestBody)
        }
    }

    fun uploadImageToFirebase(uri: Uri?, fileName: String
    ,firstName: String, email: String,
    password: String) = CoroutineScope(Dispatchers.IO).launch{
        try{
            uri?.let {
                val filePath = userProfilePicRef.child("profilePics/$fileName")
                filePath.putFile(it)
                    .addOnSuccessListener {
                        filePath.downloadUrl.addOnSuccessListener { uri ->
                            registerUser(
                                SignUpRequestBody(
                                firstName, email, password, uri.toString()
                            )
                            )
                        }
                    }
            }?.await()
            withContext(Dispatchers.Main){
                _uploadStatus.value = true
            }
        }catch (e: Exception){
            _uploadStatus.value = false
        }
    }

}

