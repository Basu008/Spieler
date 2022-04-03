package com.example.spieler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.databinding.PopularBlogItemBinding
import com.example.spieler.model.Blog

class PostsAdapter: ListAdapter<Blog, PostsAdapter.PopularBlogViewHolder>(PopularBlogDiffCallback()){

    class PopularBlogViewHolder(val binding: PopularBlogItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(blog: Blog){
            val author = "by ${blog.author_info.first_name}"
            binding.popularBlogDate.text = author
            Glide.with(binding.root.context)
                .load(blog.blog_img)
                .into(binding.imageView2)
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
        holder.bind(item)
    }
}