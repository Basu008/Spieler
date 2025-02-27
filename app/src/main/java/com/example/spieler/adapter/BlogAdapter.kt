package com.example.spieler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.BlogItemBinding
import com.example.spieler.model.Blog
import com.example.spieler.model.User
import com.example.spieler.ui.ShowSingleBlog
import com.example.spieler.util.Constants

class BlogAdapter(val user: User) : ListAdapter<Blog, BlogAdapter.BlogViewHolder>(BlogDiffCallBack()){

    class BlogViewHolder(private val binding: BlogItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(blog: Blog, user: User){
            val context = binding.blogLayout.context
            binding.blogBody.text = blog.description
            binding.blogHeading.text = blog.title
            if(blog.blog_img != null){
                Glide.with(context)
                    .load(blog.blog_img)
                    .circleCrop()
                    .into(binding.characterImage)
            }
            else{
                binding.characterImage.setImageResource(R.drawable.korg)
            }
            if(blog.author_info != null){
                binding.blogAuthor.text = blog.author_info.first_name
            }
            binding.blogDate.text = blog.time
            binding.blogLayout.setOnClickListener {
                Intent(context, ShowSingleBlog::class.java).also {
                    it.putExtra(Constants.BLOG_ID, blog._id)
                    it.putExtra(Constants.USER_ID, user._id)
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
        holder.bind(blog, user)
    }


    class BlogDiffCallBack : DiffUtil.ItemCallback<Blog>(){
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not implemented yet")
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("NOT implemented yet")
        }

    }

}