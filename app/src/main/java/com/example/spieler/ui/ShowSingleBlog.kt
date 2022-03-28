package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.ActivityShowAllBlogsBinding
import com.example.spieler.databinding.ActivityShowSingleBlogBinding
import com.example.spieler.model.Blog
import com.example.spieler.util.Constants

class ShowSingleBlog : AppCompatActivity() {

    private lateinit var binding: ActivityShowSingleBlogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_single_blog)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val blog = intent.getSerializableExtra(Constants.BLOG_DATA) as Blog
        val author = "by ${blog.author_info.first_name}"

        binding.blogViewHeading.text = blog.title
        if(blog.blog_img != null){
            Glide.with(this)
                .load(blog.blog_img)
                .fitCenter()
                .into(binding.singleBlogImage)
        }
        else{
            binding.singleBlogImage.setImageResource(R.drawable.korg)
        }
        binding.singleBlogAuthor.text = author
        binding.blogViewBody.text = blog.description
        binding.blogCreationDate.text = blog.created_at
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