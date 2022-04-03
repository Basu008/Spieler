package com.example.spieler.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.adapter.OwnPostsAdapter
import com.example.spieler.databinding.ActivityProfileBinding
import com.example.spieler.model.BlogResponseBody
import com.example.spieler.model.User
import com.example.spieler.util.Constants
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        binding.yourBlogsShimmer.startShimmer()
        setUpRecyclerView()

        val user = intent.getSerializableExtra(Constants.USER_DATA) as User
        val blogs = intent.getSerializableExtra(Constants.BLOG_DATA) as BlogResponseBody

        val username = user.first_name
        val email = user.email
        val profilePic = user.profile_img
        val adapter = OwnPostsAdapter(user)
        val posts = blogs.content.filter { it.tag != "NEWS" }
        adapter.submitList(posts.filter { it.author_info._id == user._id })
        binding.yourBlogsRv.adapter = adapter

        binding.profileUsername.text = username
        binding.profileEmail.text = email
        Glide.with(this)
            .load(profilePic.toUri())
            .circleCrop()
            .placeholder(R.drawable.user)
            .into(binding.profileDp)

        binding.yourBlogsShimmer.stopShimmer()
        binding.yourBlogsShimmer.visibility = View.GONE
        binding.yourBlogsRv.visibility = View.VISIBLE

        binding.addPostBtn.setOnClickListener {
            Intent(this, AddPostActivity::class.java).also {
                it.putExtra(Constants.USER_DATA, user)
                startActivity(it)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = ZoomRecyclerLayout(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.yourBlogsRv) // Add your recycler view here
        binding.yourBlogsRv.isNestedScrollingEnabled = false

        binding.yourBlogsRv.layoutManager = linearLayoutManager
    }
}