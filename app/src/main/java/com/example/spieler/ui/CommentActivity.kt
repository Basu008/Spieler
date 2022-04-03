package com.example.spieler.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.spieler.R
import com.example.spieler.adapter.CommentAdapter
import com.example.spieler.databinding.ActivityCommentBinding
import com.example.spieler.model.Blog
import com.example.spieler.model.Comment
import com.example.spieler.model.CommentRequestBody
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.CommentViewModel
import com.example.spieler.viewmodelfactory.CommentViewModelFactory

class CommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentBinding
    private lateinit var viewModel: CommentViewModel
    private lateinit var viewModelFactory: CommentViewModelFactory
//    private var listExist = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment)
        supportActionBar?.title = "Comments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val blogId = intent.getStringExtra(Constants.BLOG_ID)
        val currentUserId = intent.getStringExtra(Constants.USER_ID)

        val repository = Repository()
        viewModelFactory = CommentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommentViewModel::class.java]

        viewModel.updateBlog(blogId!!)
        val adapter = CommentAdapter()
        viewModel.updatedBlog.observe(this){
            adapter.submitList(it.comment)
            binding.commentsRV.adapter = adapter
        }

        binding.postComment.setOnClickListener {
            if(binding.enterComment.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Can't post empty comment", Toast.LENGTH_SHORT).show()
            }
            else{
                val commentText = binding.enterComment.text.toString().trim()
                val commentRequestBody = CommentRequestBody(currentUserId!!, blogId, commentText)
                viewModel.postComment(commentRequestBody)
            }
        }

        viewModel.commentResponse.observe(this){
            if(it.isSuccessful){
                binding.enterComment.text.clear()
                viewModel.updateBlog(blogId)
            }
        }

//        viewModel.updatedBlog.observe(this){
//            blog = it
//            adapter.submitList(blog.comment)
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            }
        return super.onOptionsItemSelected(item)
    }
}