package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.spieler.R
import com.example.spieler.adapter.CommentAdapter
import com.example.spieler.databinding.ActivityCommentBinding
import com.example.spieler.model.Blog
import com.example.spieler.model.Comment
import com.example.spieler.util.Constants

class CommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment)
        supportActionBar?.title = "Comments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val blog = intent.getSerializableExtra(Constants.BLOG_DATA) as Blog
        val currentUserId = intent.getStringExtra(Constants.USER_ID)

        val adapter = CommentAdapter()
        Toast.makeText(this, blog.comment.size.toString() , Toast.LENGTH_SHORT).show()
        adapter.submitList(blog.comment)
        binding.commentsRV.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}