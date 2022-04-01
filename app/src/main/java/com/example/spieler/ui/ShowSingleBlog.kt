package com.example.spieler.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
import com.example.spieler.viewmodel.SingleBlogViewModel
import com.example.spieler.viewmodelfactory.SingleBlogViewModelFactory

class ShowSingleBlog : AppCompatActivity() {

    private lateinit var binding: ActivityShowSingleBlogBinding
    private lateinit var viewModel: SingleBlogViewModel
    private lateinit var viewModelFactory: SingleBlogViewModelFactory

    private var blogNotLiked = true
    private var likeId = ""

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
        val author = "by ${blog.author_info.first_name}"

        blog.likes.forEach {
            if(it.user_id == currentUserId){
                binding.likeBtn.setImageResource(R.drawable.liked_24)
                likeId = it._id
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

        binding.singleBlogAuthor.text = author
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
                        likeId = it.body()?.content?._id!!
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
                viewModel.dislikeBlog(likeId, DeleteLikeRequestBody(blog._id))
                viewModel.postDisliked.observe(this){
                    if(it.isSuccessful){
//
                    }
                }
                binding.likeBtn.setImageResource(R.drawable.unliked_24)
                blogNotLiked = true
            }
        }

        binding.commentBtn.setOnClickListener {
            Intent(this, CommentActivity::class.java).also{
                it.putExtra(Constants.BLOG_DATA, blog)
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