package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.spieler.R
import com.example.spieler.adapter.NewsAdapter
import com.example.spieler.databinding.ActivityNewsBinding
import com.example.spieler.model.Blog
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
            .sortedByDescending { it.created_at }
        val gamesNews = newsList.filter { it.category == "Games"}
        val eSportsNews = newsList.filter { it.category == "Esports"}
        val tournamentNews = newsList.filter { it.category == "Tournament"}
        var activeList = listOf<Blog>()

        val adapter = NewsAdapter()
        adapter.submitList(newsList)
        binding.newsRV.adapter = adapter
        binding.newsCategoryChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            checkedIds.forEach {
                if(it == R.id.allChip){
                    activeList = newsList
                    adapter.submitList(newsList)
                }
                if(it == R.id.gamesChip){
                    activeList = gamesNews
                    adapter.submitList(gamesNews)
                }
                if(it == R.id.eSportsChip){
                    activeList = eSportsNews
                    adapter.submitList(eSportsNews)
                }
                if(it == R.id.tournamentChip){
                    activeList = tournamentNews
                    adapter.submitList(tournamentNews)
                }
            }

        }

        binding.newsSearchInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.submitList(activeList.filter { it.description.contains(p0!!, true)
                        || it.title.contains(p0, true)})

                if(p0.isNullOrBlank()){
                    adapter.submitList(activeList)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

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