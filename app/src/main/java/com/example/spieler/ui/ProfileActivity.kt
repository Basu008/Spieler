@file:Suppress("DEPRECATION")

package com.example.spieler.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.adapter.OwnUploadsAdapter
import com.example.spieler.databinding.ActivityProfileBinding
import com.example.spieler.model.BlogResponseBody
import com.example.spieler.model.User
import com.example.spieler.util.Constants

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    var user: User? =  null
    var blogs: BlogResponseBody? = null
    var currentUsedId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f

        user = intent.getSerializableExtra(Constants.USER_DATA) as User
        currentUsedId = intent.getStringExtra(Constants.USER_ID)
        blogs = intent.getSerializableExtra(Constants.BLOG_DATA) as BlogResponseBody

        val uploads = blogs?.content?.filter { it.tag != "NEWS" && it.author_info._id == user?._id}
        val ownBlogs = uploads?.filter { it.tag == "BLOG" }
        val ownPosts = uploads?.filter { it.tag == "POST" }

        val username = user?.first_name
        val email = user?.email
        val profilePic = user?.profile_img
        val adapter = OwnUploadsAdapter(user!!)

        setPostsTab()
        adapter.submitList(ownPosts)
        binding.yourUploadsRV.layoutManager = GridLayoutManager(this, 2)
        binding.yourUploadsRV.adapter = adapter


        binding.profileUsername.text = username
        binding.profileEmail.text = email
        Glide.with(this)
            .load(profilePic?.toUri())
            .circleCrop()
            .placeholder(R.drawable.user)
            .into(binding.profileDp)
        binding.uploadsCount.text = uploads?.size.toString()
        binding.followersCount.text = user?.followers?.toString() ?: "0"
        binding.followingCount.text = user?.following?.toString() ?: "0"
        binding.postOptionHeading.setOnClickListener {
            setPostsTab()
            adapter.submitList(ownPosts)
        }

        binding.blogOptionHeading.setOnClickListener {
            setBlogsTab()
            adapter.submitList(ownBlogs)
        }
    }

    private fun setPostsTab(){
        binding.postOptionHeading.setTextColor(resources.getColor(R.color.white))
        binding.postOptionHeading.setBackgroundResource(R.drawable.profile_heading_selected_bg)
        binding.blogOptionHeading.setTextColor(resources.getColor(R.color.black))
        binding.blogOptionHeading.setBackgroundResource(R.drawable.profile_heading_bg)
    }

    private fun setBlogsTab(){
        binding.blogOptionHeading.setTextColor(resources.getColor(R.color.white))
        binding.blogOptionHeading.setBackgroundResource(R.drawable.profile_heading_selected_bg)
        binding.postOptionHeading.setTextColor(resources.getColor(R.color.black))
        binding.postOptionHeading.setBackgroundResource(R.drawable.profile_heading_bg)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if(currentUsedId == user?._id) {
                    Intent(this, HomeActivity::class.java).also {
                        it.putExtra(Constants.USER_DATA, user)
                        it.putExtra(Constants.BLOG_DATA, blogs)
                        startActivity(it)
                        finish()
                    }
                }
                else{
                    finish()
                }
            }
            R.id.miEdit ->  {
                Intent(this, EditProfileActivity::class.java).also {
                    it.putExtra(Constants.USER_DATA, user)
                    it.putExtra(Constants.BLOG_DATA, blogs)
                    startActivity(it)
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(currentUsedId == user?._id){
            menuInflater.inflate(R.menu.edit_menu, menu)
        }
        return true
    }

    override fun onBackPressed() {
        if(currentUsedId == user?._id) {
            Intent(this, HomeActivity::class.java).also {
                it.putExtra(Constants.USER_DATA, user)
                it.putExtra(Constants.BLOG_DATA, blogs)
                startActivity(it)
                finish()
            }
        }
        else{
            finish()
        }
    }
}