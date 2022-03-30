package com.example.spieler.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.ActivityProfileBinding
import com.example.spieler.model.User
import com.example.spieler.util.Constants

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sessionSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        binding.yourBlogsShimmer.startShimmer()
        sessionSharedPreferences = getSharedPreferences(Constants.USER_SESSION, Context.MODE_PRIVATE)
        val user = intent.getSerializableExtra(Constants.USER_DATA) as User

        val username = user.first_name
        val email = user.email
        val profilePic = user.profile_img

        binding.profileUsername.text = username
        binding.profileEmail.text = email
        Glide.with(this)
            .load(profilePic.toUri())
            .circleCrop()
            .placeholder(R.drawable.user)
            .into(binding.profileDp)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}