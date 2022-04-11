package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.spieler.R
import com.example.spieler.adapter.AllUsersAdapter
import com.example.spieler.databinding.ActivityShowAllUsersBinding
import com.example.spieler.model.BlogResponseBody
import com.example.spieler.model.FollowingDataSet
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.AllUsersViewModel
import com.example.spieler.viewmodelfactory.AllUsersViewModelFactory

class ShowAllUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowAllUsersBinding
    private lateinit var viewModel: AllUsersViewModel
    private lateinit var viewModelFactory: AllUsersViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_all_users)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val repository = Repository()
        viewModelFactory = AllUsersViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AllUsersViewModel::class.java]

        val userId = intent.getStringExtra(Constants.USER_ID)
        val blogs = intent.getSerializableExtra(Constants.BLOG_DATA) as BlogResponseBody
        val followDataSet = intent.getSerializableExtra(Constants.ALL_FOLLOWERS) as FollowingDataSet
        val adapter = AllUsersAdapter(blogs, userId!!, followDataSet)
        viewModel.getAllUsers()

        binding.allUserRV.adapter = adapter

        viewModel.allUsers.observe(this){
            if(it.isSuccessful){
                val users = it.body()?.content?.filter { user ->  user._id != userId }
                adapter.submitList(users)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}