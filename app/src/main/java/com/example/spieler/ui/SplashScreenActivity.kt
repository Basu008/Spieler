package com.example.spieler.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.spieler.R
import com.example.spieler.util.Constants

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var sessionSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        sessionSharedPreferences = getSharedPreferences(Constants.USER_SESSION, Context.MODE_PRIVATE)!!
        val userId = sessionSharedPreferences.getString(Constants.USER_ID, "")

        Handler().postDelayed({
            if(userId != ""){
                Intent(this, HomeActivity::class.java).also {
                    it.putExtra(Constants.USER_ID, userId)
                    startActivity(it)
                    finish()
                }
            }
            else{
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }, 2500)

    }
}