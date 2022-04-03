package com.example.spieler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.databinding.PopularBlogItemBinding
import com.example.spieler.model.Blog
import com.example.spieler.model.User
import com.example.spieler.ui.ShowSingleBlog
import com.example.spieler.ui.ShowSinglePost
import com.example.spieler.util.Constants

class PostsAdapter(private val user: User): ListAdapter<Blog, PostsAdapter.PopularBlogViewHolder>(PopularBlogDiffCallback()){

    class PopularBlogViewHolder(val binding: PopularBlogItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(blog: Blog, user: User){
            val context = binding.root.context
            val author = "by ${blog.author_info.first_name}"
            binding.popularBlogDate.text = author
            Glide.with(context)
                .load(blog.blog_img)
                .into(binding.imageView2)
            binding.postLayout.setOnClickListener {
                Intent(context, ShowSinglePost::class.java).also {
                    it.putExtra(Constants.BLOG_ID, blog._id)
                    it.putExtra(Constants.USER_ID, user._id)
                    context.startActivity(it)
                }
            }
        }

        companion object{
            fun from(parent: ViewGroup): PopularBlogViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularBlogItemBinding.inflate(layoutInflater, parent, false)
                return PopularBlogViewHolder(binding)
            }
        }
    }

    class PopularBlogDiffCallback: DiffUtil.ItemCallback<Blog>(){
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularBlogViewHolder {
        return PopularBlogViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PopularBlogViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, user)
    }
}