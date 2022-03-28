package com.example.spieler.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.adapter.BlogAdapter
import com.example.spieler.databinding.ActivityHomeBinding
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.HomeViewModel
import com.example.spieler.viewmodelfactory.HomeViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var sessionSharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout,
            R.string.open, R.string.close)
        toggle.syncState()
        binding.drawerLayout.addDrawerListener(toggle)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sessionSharedPreferences = getSharedPreferences(Constants.USER_SESSION, Context.MODE_PRIVATE)
        editor = sessionSharedPreferences.edit()

        val userId = sessionSharedPreferences.getString(Constants.USER_ID, "")
        val username = sessionSharedPreferences.getString(Constants.USER_FIRST_NAME, "")
        val email = sessionSharedPreferences.getString(Constants.USER_EMAIL, "")
        val profilePic = sessionSharedPreferences.getString(Constants.USER_PROFILE_PIC, "defaultPic")

        //Update data to UI
        val headerLayout = binding.navView.getHeaderView(0)
        val headerProfilePic: ImageView = headerLayout.findViewById(R.id.dp_navDrawer)
        val headerUsername: TextView = headerLayout.findViewById(R.id.username_navDrawer)
        val headerEmail: TextView = headerLayout.findViewById(R.id.email_navDrawer)

        Glide.with(this)
            .load(profilePic?.toUri())
            .circleCrop()
            .placeholder(R.drawable.user)
            .into(headerProfilePic)
        headerUsername.text = username!!
        headerEmail.text = email!!

        binding.homePageLayout.popularBlogShimmer.startShimmer()
        binding.homePageLayout.recentBlogsShimmer.startShimmer()

        binding.homePageLayout.addBlogBtn.setOnClickListener {
            Intent(this, AddBlogActivity::class.java).also {
                startActivity(it)
            }
        }

        //On interacting with nav drawer menu
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.miYourProfile -> {
                    Intent(this, ProfileActivity::class.java).also{
                        startActivity(it)
                    }
                }
                R.id.miSavedBlog -> {
                    Intent(this, ShowAllBlogsActivity::class.java).also{
                        it.putExtra(Constants.TYPE_OF_BLOGS_TO_BE_DISPLAYED, Constants.SAVED_BLOGS)
                        startActivity(it)
                    }
                }
                R.id.miLogOut -> {
                    editor.apply {
                        remove(Constants.USER_ID)
                        apply()
                    }
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }
            }
            true
        }

        val repository = Repository()
        viewModelFactory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        viewModel.getAllBlogs()

        viewModel.allBlogs.observe(this){
            if(it.isSuccessful){
                val blogAdapter = BlogAdapter()
                blogAdapter.submitList(it.body()?.content)
                binding.homePageLayout.recentBlogsShimmer.stopShimmer()
                binding.homePageLayout.allBlogsRv.adapter = blogAdapter
                binding.homePageLayout.recentBlogsShimmer.visibility = View.GONE
                binding.homePageLayout.allBlogsRv.visibility = View.VISIBLE
            }
            else{
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    //To make sure the user can drag as well as tap on the hamburger icon to open nav drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}