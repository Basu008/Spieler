package com.example.spieler.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.ActivityShowSinglePostBinding
import com.example.spieler.model.DeleteLikeRequestBody
import com.example.spieler.model.LikeRequestBody
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.SingleBlogViewModel
import com.example.spieler.viewmodelfactory.SingleBlogViewModelFactory

class ShowSinglePost : AppCompatActivity() {

    private lateinit var binding: ActivityShowSinglePostBinding
    private lateinit var viewModel: SingleBlogViewModel
    private lateinit var viewModelFactory: SingleBlogViewModelFactory

    private var blogNotLiked = true
    private var likeId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_single_post)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val repository = Repository()
        viewModelFactory = SingleBlogViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[
                SingleBlogViewModel::class.java
        ]


        val blogId = intent.getStringExtra(Constants.BLOG_ID)!!
        val currentUserId = intent.getStringExtra(Constants.USER_ID)!!

        viewModel.updateBlog(blogId)
        viewModel.updatedBlog.observe(this){post ->
            binding.postUsername.text = post.author_info.first_name
            binding.postCaption.text = post.description
            binding.postCreationDate.text = post.created_at
            Glide.with(this)
                .load(post.author_info.profile_img)
                .into(binding.postProfilePic)
            Glide.with(this)
                .load(post.blog_img)
                .into(binding.postImage)

        }

        binding.postLikeBtn.setOnClickListener {
            if(blogNotLiked){
                viewModel.likeBlog(LikeRequestBody(currentUserId, blogId))
//                editor.apply{
//                    putBoolean(blog._id, true)
//                    apply()
//                }
                viewModel.postLiked.observe(this){
                    if(it.isSuccessful){
                        likeId = it.body()?.content?._id!!
                    }
                }
                binding.postLikeBtn.setImageResource(R.drawable.liked_24)
                blogNotLiked = false
            }
            else{
//                editor.apply(){
//                    remove(blog._id)
//                    apply()
//                }
                viewModel.dislikeBlog(likeId, DeleteLikeRequestBody(blogId))
                viewModel.postDisliked.observe(this){
                    if(it.isSuccessful){
//
                    }
                }
                binding.postLikeBtn.setImageResource(R.drawable.unliked_24)
                blogNotLiked = true
            }
        }

        binding.postCommentBtn.setOnClickListener {
            Intent(this, CommentActivity::class.java).also{
                it.putExtra(Constants.BLOG_ID, blogId)
                it.putExtra(Constants.USER_ID, currentUserId)
                startActivity(it)
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
}