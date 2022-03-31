package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.ActivityShowSingleNewsBinding
import com.example.spieler.model.Blog
import com.example.spieler.util.Constants

class ShowSingleNews : AppCompatActivity() {

    private lateinit var binding: ActivityShowSingleNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_single_news)

        val blog = intent.getSerializableExtra(Constants.BLOG_DATA) as Blog

        binding.newsViewHeading.text = blog.title
        binding.newsViewBody.text = blog.description
        binding.newsCreationDate.text = blog.created_at
        Glide.with(this)
            .load(blog.blog_img)
            .into(binding.singleNewsImage)
    }
}