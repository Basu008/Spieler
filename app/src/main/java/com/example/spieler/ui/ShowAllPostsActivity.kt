package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.spieler.R
import com.example.spieler.databinding.ActivityShowAllPostsBinding

class ShowAllPostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowAllPostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_all_posts)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Posts"
        supportActionBar?.elevation = 0f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}