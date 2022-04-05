package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.spieler.R
import com.example.spieler.adapter.NewsAdapter
import com.example.spieler.databinding.ActivityNewsBinding
import com.example.spieler.model.BlogResponseBody
import com.example.spieler.model.User
import com.example.spieler.util.Constants

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "NEWS"

        val blogResponse = intent.getSerializableExtra(Constants.BLOG_DATA) as BlogResponseBody
        val user = intent.getSerializableExtra(Constants.USER_DATA) as User
        val newsList = blogResponse.content.filter { it.tag == "NEWS" }

        val adapter = NewsAdapter()
        adapter.submitList(newsList)
        binding.newsRV.adapter = adapter
        binding.newsCategoryChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            checkedIds.forEach {
                if(it == R.id.allChip){
                    adapter.submitList(newsList)
                }
                if(it == R.id.gamesChip){
                    adapter.submitList(newsList.filter { it.category == "Games"})
                }
                if(it == R.id.eSportsChip){
                    adapter.submitList(newsList.filter { it.category == "Esports"})
                }
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