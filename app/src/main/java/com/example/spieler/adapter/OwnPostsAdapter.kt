package com.example.spieler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.databinding.OwnPostItemBinding
import com.example.spieler.databinding.PopularBlogItemBinding
import com.example.spieler.model.Blog
import com.example.spieler.model.User
import com.example.spieler.ui.ShowSinglePost
import com.example.spieler.util.Constants

class OwnPostsAdapter(private val user: User): ListAdapter<Blog, OwnPostsAdapter.OwnPostViewHolder>(OwnPostDiffUtil()) {

    class OwnPostViewHolder(val binding: OwnPostItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(blog: Blog, user: User){
            val context = binding.root.context
            Glide.with(context)
                .load(blog.blog_img)
                .into(binding.userPostPic)
            binding.ownPostLayout.setOnClickListener {
                Intent(context, ShowSinglePost::class.java).also {
                    it.putExtra(Constants.BLOG_ID, blog._id)
                    it.putExtra(Constants.USER_ID, user._id)
                    context.startActivity(it)
                }
            }
        }

        companion object{
            fun from(parent: ViewGroup): OwnPostViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OwnPostItemBinding.inflate(layoutInflater, parent, false)
                return OwnPostViewHolder(binding)
            }
        }
    }

    class OwnPostDiffUtil: DiffUtil.ItemCallback<Blog>(){
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            TODO("Not yet implemented")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnPostViewHolder {
        return OwnPostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OwnPostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post, user)
    }
}