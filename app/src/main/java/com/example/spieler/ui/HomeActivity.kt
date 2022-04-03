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
import com.example.spieler.model.Blog
import com.example.spieler.model.User
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

    private lateinit var news: List<Blog>


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

        val user = intent.getSerializableExtra(Constants.USER_DATA) as User
        val userId = user._id
        val username = user.first_name
        val email = user.email
        val profilePic = user.profile_img

        //Update data to UI
        val headerLayout = binding.navView.getHeaderView(0)
        val headerProfilePic: ImageView = headerLayout.findViewById(R.id.dp_navDrawer)
        val headerUsername: TextView = headerLayout.findViewById(R.id.username_navDrawer)
        val headerEmail: TextView = headerLayout.findViewById(R.id.email_navDrawer)

        Glide.with(this)
            .load(profilePic.toUri())
            .circleCrop()
            .placeholder(R.drawable.user)
            .into(headerProfilePic)
        headerUsername.text = username
        headerEmail.text = email

        binding.homePageLayout.popularBlogShimmer.startShimmer()
        binding.homePageLayout.recentBlogsShimmer.startShimmer()

        binding.homePageLayout.addBlogBtn.setOnClickListener {
            Intent(this, AddBlogActivity::class.java).also {
                it.putExtra(Constants.USER_DATA, user)
                startActivity(it)
            }
        }

        val newsIntent = Intent(this, NewsActivity::class.java)
        newsIntent.putExtra(Constants.USER_DATA, user)

        //On interacting with nav drawer menu
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.miYourProfile -> {
                    Intent(this, ProfileActivity::class.java).also{
                        it.putExtra(Constants.USER_DATA, user)
                        startActivity(it)
                    }
                }
                R.id.miNews -> {
                    startActivity(newsIntent)
                }
                R.id.miLogOut -> {
                    editor.apply {
                        remove(Constants.USER_ID)
                        remove(Constants.USER_EMAIL)
                        remove(Constants.USER_PASSWORD)
                        apply()
                    }
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

        viewModel.allBlogs.observe(this){response ->
            if(response.isSuccessful){
                val blogAdapter = BlogAdapter(user)
                newsIntent.putExtra(Constants.BLOG_DATA, response.body())
                val blogs = response.body()?.content?.sortedByDescending { it.created_at }!!
                blogAdapter.submitList(blogs.filter { it.tag == "BLOG" })
                news = blogs.filter { it.tag == "NEWS" }
                binding.homePageLayout.recentBlogsShimmer.stopShimmer()
                binding.homePageLayout.allBlogsRv.adapter = blogAdapter
                binding.homePageLayout.recentBlogsShimmer.visibility = View.GONE
                binding.homePageLayout.allBlogsRv.visibility = View.VISIBLE
            }
            else{
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()
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

    override fun onRestart() {
        viewModel.getAllBlogs()
        super.onRestart()
    }
}