package com.example.spieler.ui

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment)
        supportActionBar?.title = "Comments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val blog = intent.getSerializableExtra(Constants.BLOG_DATA) as Blog
        val currentUserId = intent.getStringExtra(Constants.USER_ID)

        val repository = Repository()
        viewModelFactory = CommentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommentViewModel::class.java]

        val adapter = CommentAdapter()
        Toast.makeText(this, blog.comment.size.toString() , Toast.LENGTH_SHORT).show()
        adapter.submitList(blog.comment)
        binding.commentsRV.adapter = adapter

        binding.postComment.setOnClickListener {
            if(binding.enterComment.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Can't post empty comment", Toast.LENGTH_SHORT).show()
            }
            else{
                val commentText = binding.enterComment.text.toString().trim()
                val commentRequestBody = CommentRequestBody(currentUserId!!, blog._id, commentText)
                viewModel.postComment(commentRequestBody)
            }
        }

        viewModel.commentResponse.observe(this){
            if(it.isSuccessful){

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}