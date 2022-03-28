package com.example.spieler.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spieler.repository.Repository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class AddBlogViewModel(private val repository: Repository): ViewModel() {
    private val blogImageStorageRef = Firebase.storage.reference
    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean>
        get() = _uploadStatus

    fun uploadImageToFirebase(uri: Uri, fileName: String) = CoroutineScope(Dispatchers.IO).launch{
        try{
            uri.let {
                blogImageStorageRef.child("blogImages/$fileName").putFile(it)
            }.await()
            withContext(Dispatchers.Main){
                _uploadStatus.value = true
            }
        }catch (e: Exception){
            _uploadStatus.value = false
        }
    }
}