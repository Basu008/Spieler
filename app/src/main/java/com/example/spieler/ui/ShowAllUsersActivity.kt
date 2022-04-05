package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.spieler.R
import com.example.spieler.databinding.ActivityShowAllUsersBinding

class ShowAllUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowAllUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_all_users)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Users"
        supportActionBar?.elevation = 0f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}