@file:Suppress("DEPRECATION")

package com.example.spieler.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.spieler.R
import com.example.spieler.model.LoginRequestBody
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.LoginViewModel
import com.example.spieler.viewmodelfactory.LoginViewModelFactory

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var sessionSharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        val repository = Repository()
        viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        sessionSharedPreferences = getSharedPreferences(Constants.USER_SESSION, Context.MODE_PRIVATE)!!
        editor = sessionSharedPreferences.edit()
        val userId = sessionSharedPreferences.getString(Constants.USER_ID, "")
        val email = sessionSharedPreferences.getString(Constants.USER_EMAIL, "")
        val password = sessionSharedPreferences.getString(Constants.USER_PASSWORD, "")
        Handler().postDelayed({
            if(userId != "" && email != "" && password != ""){
                viewModel.loginUser(LoginRequestBody(email!!, password!!))
                viewModel.currentUser.observe(this){
                    if(it.isSuccessful){
                        Toast.makeText(this, it.body()?.content?.token, Toast.LENGTH_SHORT).show()
                        viewModel.getCurrentUserDetails(userId!!)
                        viewModel.userDetails.observe(this){user ->
                            if(user != null){

                                editor.apply {
                                    putBoolean(Constants.USER_SESSION_ACTIVE, true)
                                    putString(Constants.USER_ID, userId)
                                    putString(Constants.USER_EMAIL, email)
                                    putString(Constants.USER_PASSWORD, password)
                                    apply()
                                }
                                Intent(this, HomeActivity::class.java).also {intent ->
                                    intent.putExtra(Constants.USER_DATA, user)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                            else{
                                startMainActivity()
                            }
                        }

                    }
                    else{
                        startMainActivity()
                    }
                }

            }
            else{
               startMainActivity()
            }
        }, 3000)

    }

    private fun startMainActivity(){
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}