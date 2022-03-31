package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.spieler.R
import com.example.spieler.databinding.ActivityShowAllBlogsBinding
import com.example.spieler.util.Constants

class ShowAllBlogsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowAllBlogsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_all_blogs)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val blogsType = intent.getStringExtra(Constants.TITLE_TO_BE_DISPLAYED)
        supportActionBar?.title = blogsType
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