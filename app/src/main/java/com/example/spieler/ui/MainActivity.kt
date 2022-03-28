package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.spieler.R
import com.example.spieler.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var backPressedOnce = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.
            setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        if(backPressedOnce){
            super.onBackPressed()
        }
        else{
            Toast.makeText(this, "Press back again to exit the app", Toast.LENGTH_SHORT).show()
            backPressedOnce = true
        }

    }
}