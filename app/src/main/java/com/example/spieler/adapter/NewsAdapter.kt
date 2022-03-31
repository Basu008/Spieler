package com.example.spieler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.databinding.NewsItemBinding
import com.example.spieler.model.Blog

class NewsAdapter: ListAdapter<Blog, NewsAdapter.NewsViewHolder>(NewsDiffUtil()){
    class NewsViewHolder(private val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(blog: Blog){
            val context = binding.newsLayout.context
            binding.newsBody.text = blog.description
            binding.newsHeading.text = blog.title
            binding.newsDate.text = blog.created_at
            Glide.with(context)
                .load(blog.blog_img)
                .into(binding.newsImage)
        }

        companion object{
            fun from(viewGroup: ViewGroup): NewsViewHolder{
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = NewsItemBinding.inflate(layoutInflater, viewGroup, false)
                return NewsViewHolder(binding)
            }
        }
    }

    class NewsDiffUtil: DiffUtil.ItemCallback<Blog>(){
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not yet implemented")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val blog = getItem(position)
        holder.bind(blog)
    }
}