package com.example.spieler.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.ActivityShowSingleBlogBinding
import com.example.spieler.model.Blog
import com.example.spieler.model.DeleteLikeRequestBody
import com.example.spieler.model.LikeRequestBody
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.HomeViewModel
import com.example.spieler.viewmodel.SingleBlogViewModel
import com.example.spieler.viewmodelfactory.HomeViewModelFactory
import com.example.spieler.viewmodelfactory.SingleBlogViewModelFactory

class ShowSingleBlog : AppCompatActivity() {

    private lateinit var binding: ActivityShowSingleBlogBinding
    private lateinit var viewModel: SingleBlogViewModel
    private lateinit var viewModelFactory: SingleBlogViewModelFactory

    private var blogNotLiked = true
    private var like_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_single_blog)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val repository = Repository()
        viewModelFactory = SingleBlogViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[
                SingleBlogViewModel::class.java
        ]


        val blog = intent.getSerializableExtra(Constants.BLOG_DATA) as Blog
        val currentUserId = intent.getStringExtra(Constants.USER_ID)!!
        if(blog.author_info != null){
            val author = "by ${blog.author_info.first_name}"
        }

        blog.likes.forEach {
            if(it.user_id == currentUserId){
                binding.likeBtn.setImageResource(R.drawable.liked_24)
                like_id = it._id
                blogNotLiked = false
            }
        }

        binding.blogViewHeading.text = blog.title
        if(blog.blog_img != null){
            Glide.with(this)
                .load(blog.blog_img.toUri())
                .fitCenter()
                .into(binding.singleBlogImage)
        }
        else{
            binding.singleBlogImage.setImageResource(R.drawable.korg)
        }

//        binding.singleBlogAuthor.text = author
        binding.blogViewBody.text = blog.description
        binding.blogCreationDate.text = blog.created_at

        binding.likeBtn.setOnClickListener {
            if(blogNotLiked){
                viewModel.likeBlog(LikeRequestBody(currentUserId, blog._id))
//                editor.apply{
//                    putBoolean(blog._id, true)
//                    apply()
//                }
                viewModel.postLiked.observe(this){
                    if(it.isSuccessful){
                        like_id = it.body()?.content?._id!!
                    }
                }
                binding.likeBtn.setImageResource(R.drawable.liked_24)
                blogNotLiked = false
            }
            else{
//                editor.apply(){
//                    remove(blog._id)
//                    apply()
//                }
                viewModel.dislikeBlog(like_id, DeleteLikeRequestBody(blog._id))
                viewModel.postDisliked.observe(this){
                    if(it.isSuccessful){
//
                    }
                }
                binding.likeBtn.setImageResource(R.drawable.unliked_24)
                blogNotLiked = true
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