package com.example.spieler.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.ActivityAddBlogBinding
import com.example.spieler.databinding.CustomProgressDialogBinding
import com.example.spieler.model.PostBlogBody
import com.example.spieler.model.User
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.AddBlogViewModel
import com.example.spieler.viewmodelfactory.AddBlogViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AddBlogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBlogBinding
    private lateinit var viewModel: AddBlogViewModel
    private lateinit var viewModelFactory: AddBlogViewModelFactory
    private lateinit var sessionSharedPreferences: SharedPreferences
    private lateinit var playerNameDialog: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    var currFile: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_blog)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val blogImageResult = registerForActivityResult(ActivityResultContracts.GetContent()){
            if(it != null){
                binding.uploadImageButton.visibility = View.GONE
                currFile = it
                Glide.with(this)
                    .load(it)
                    .centerCrop()
                    .into(binding.blogImageUpload)
            }
        }

        val repository = Repository()
        viewModelFactory = AddBlogViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddBlogViewModel::class.java]
        sessionSharedPreferences = getSharedPreferences(Constants.USER_SESSION, Context.MODE_PRIVATE)

        val user = intent.getSerializableExtra(Constants.USER_DATA) as User
        val userId: String = user._id

        binding.uploadImageButton.setOnClickListener{
            blogImageResult.launch("image/*")
        }

        binding.submitBlogBtn.setOnClickListener {
            val title = binding.blogHeadingInput.text.toString().trim()
            val body = binding.blogBodyInput.text.toString().trim()
            if(body.isEmpty() || title.isEmpty()){
                Toast.makeText(this, "Incomplete Article!", Toast.LENGTH_SHORT).show()
            }
            else{
                createDialog("Uploading your Article")
                val date = SimpleDateFormat("yyMMddHHmmss", Locale.getDefault()).format(Date())
                if(currFile != null){
                    viewModel.uploadImageToFirebase(currFile!!, date,
                    title, body, userId, "BLOG")
                }
                else{
                    val postBlogBody = PostBlogBody(title, body, userId, "defaultPic", "BLOG")
                    viewModel.postBlog(postBlogBody)
                }
            }

        }

        viewModel.postBlogResponse.observe(this){
            if(it.isSuccessful){
                dialog.dismiss()
                Toast.makeText(this, "Blog created", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createDialog(message: String){
        playerNameDialog = AlertDialog.Builder(this)
        val newBinding: CustomProgressDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.custom_progress_dialog, null, false)
        playerNameDialog.setView(newBinding.root)
        dialog = playerNameDialog.create()
        newBinding.message.text = message
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}