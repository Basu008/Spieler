package com.example.spieler.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spieler.model.PostBlogBody
import com.example.spieler.model.PostBlogResponse
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

class AddBlogViewModel(private val repository: Repository): ViewModel() {
    private val blogImageStorageRef = Firebase.storage.reference
    private val _postBlogResponse = MutableLiveData<Response<PostBlogResponse>>()
    val postBlogResponse: LiveData<Response<PostBlogResponse>>
        get() = _postBlogResponse

    fun postBlog(postBlogBody: PostBlogBody){
        viewModelScope.launch {
            val response = repository.postBlog(postBlogBody)
            _postBlogResponse.value = response
        }
    }

    fun uploadImageToFirebase(uri: Uri, fileName: String,
    title: String, description: String,
    id: String) = CoroutineScope(Dispatchers.IO).launch{
        try{
            uri.let {
                val filePath = blogImageStorageRef.child("blogImages/$fileName")
                filePath.putFile(it)
                    .addOnSuccessListener {
                        filePath.downloadUrl.addOnSuccessListener {
                            postBlog(PostBlogBody(title, description, id, uri.toString()))
                        }
                    }
            }.await()

        }catch (e: Exception){

        }
    }
}