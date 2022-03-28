package com.example.spieler.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spieler.R
import com.example.spieler.databinding.BlogItemBinding
import com.example.spieler.model.Blog
import com.example.spieler.ui.ShowAllBlogsActivity
import com.example.spieler.ui.ShowSingleBlog
import com.example.spieler.util.Constants

class BlogAdapter : ListAdapter<Blog, BlogAdapter.BlogViewHolder>(BlogDiffCallBack()){

    class BlogViewHolder(private val binding: BlogItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(blog: Blog){
            val context = binding.blogLayout.context
            binding.blogBody.text = blog.description
            binding.blogHeading.text = blog.title
            binding.characterImage.setImageResource(R.drawable.korg)
            binding.blogAuthor.text = blog.author_info.first_name
            binding.blogDate.text = blog.created_at
            binding.blogLayout.setOnClickListener {
                Intent(context, ShowSingleBlog::class.java).also {
                    it.putExtra(Constants.BLOG_DATA, blog)
                    context.startActivity(it)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): BlogViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BlogItemBinding.inflate(layoutInflater, parent, false)
                return BlogViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = getItem(position)
        holder.bind(blog)
    }


    class BlogDiffCallBack : DiffUtil.ItemCallback<Blog>(){
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not yet implemented")
        }

    }

}