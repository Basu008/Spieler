package com.example.spieler.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.ActivityEditProfileBinding
import com.example.spieler.databinding.CustomProgressDialogBinding
import com.example.spieler.model.BlogResponseBody
import com.example.spieler.model.UpdateUser
import com.example.spieler.model.User
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.EditProfileViewModel
import com.example.spieler.viewmodelfactory.EditProfileViewModelFactory
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel
    private lateinit var viewModelFactory: EditProfileViewModelFactory
    private lateinit var playerNameDialog: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    private var user: User? = null
    private var blogs: BlogResponseBody? = null
    private var currUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f

        val profilePicResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
            if(it != null){
                currUri = it
                Glide.with(this)
                    .load(it)
                    .circleCrop()
                    .into(binding.editProfiePic)
            }
        }

        val repository = Repository()
        viewModelFactory = EditProfileViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[
            EditProfileViewModel::class.java
        ]

        user = intent.getSerializableExtra(Constants.USER_DATA) as User
        blogs = intent.getSerializableExtra(Constants.BLOG_DATA) as BlogResponseBody
        Glide.with(this)
            .load(user?.profile_img)
            .placeholder(R.drawable.user)
            .into(binding.editProfiePic)
        binding.editUsername.setText(user?.first_name)
        binding.editEmail.setText(user?.email)

        binding.editProfiePicButton.setOnClickListener {
            profilePicResultLauncher.launch("image/*")
        }

        viewModel.getUpdatedUser.observe(this){
            if(it.isSuccessful){
                dialog.dismiss()
                user = it.body()?.content
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
            }
            else{
                dialog.dismiss()
                Toast.makeText(this, "Profile can't be updated. Try again", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                Intent(this, ProfileActivity::class.java).also {
                    it.putExtra(Constants.USER_DATA, user)
                    it.putExtra(Constants.USER_ID, user?._id)
                    it.putExtra(Constants.BLOG_DATA, blogs)
                    startActivity(it)
                    finish()
                }
            }
            R.id.miSubmit ->  {
                createDialog("Updating your profile..")
                if(currUri != null){
                    viewModel.uploadImageToFirebase(currUri,
                        binding.editEmail.text.toString().trim(),
                        binding.editUsername.text.toString().trim()
                            .replaceFirstChar
                            { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        binding.editEmail.text.toString().trim(),
                        user?._id!!
                    )
                }
                else{
                    viewModel.editUser(user?._id!!,
                        UpdateUser(binding.editUsername.text.toString().trim()
                            .replaceFirstChar
                            { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            binding.editEmail.text.toString().trim(),
                            user?.profile_img!!
                        )
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        Intent(this, ProfileActivity::class.java).also {
            it.putExtra(Constants.USER_DATA, user)
            it.putExtra(Constants.USER_ID, user?._id)
            it.putExtra(Constants.BLOG_DATA, blogs)
            startActivity(it)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.submit_changes_menu, menu)
        return true
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