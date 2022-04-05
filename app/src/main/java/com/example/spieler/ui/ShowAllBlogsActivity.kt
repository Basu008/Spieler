package com.example.spieler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.spieler.R
import com.example.spieler.adapter.BlogAdapter
import com.example.spieler.databinding.ActivityShowAllBlogsBinding
import com.example.spieler.model.BlogResponseBody
import com.example.spieler.model.User
import com.example.spieler.util.Constants

class ShowAllBlogsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowAllBlogsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_all_blogs)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Blogs"
        supportActionBar?.elevation = 0f

        val user = intent.getSerializableExtra(Constants.USER_DATA) as User
        val blogs = intent.getSerializableExtra(Constants.BLOG_DATA) as BlogResponseBody

        val blogAdapter = BlogAdapter(user)
        val ownBlogs = blogs.content.filter { it.tag == "BLOG" }
            .sortedByDescending { it.created_at }
        blogAdapter.submitList(ownBlogs)
        binding.allBlogsRV.adapter = blogAdapter

        binding.blogSearchInput.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                blogAdapter.submitList(ownBlogs.filter { it.description.contains(p0!!, true)
                        || it.title.contains(p0, true)})

                if(p0.isNullOrBlank()){
                    blogAdapter.submitList(ownBlogs)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}