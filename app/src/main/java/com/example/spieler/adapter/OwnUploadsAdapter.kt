package com.example.spieler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.databinding.OwnUploadItemBinding
import com.example.spieler.model.Blog
import com.example.spieler.model.User
import com.example.spieler.ui.ShowSinglePost
import com.example.spieler.util.Constants

class OwnUploadsAdapter(private val user: User): ListAdapter<Blog, OwnUploadsAdapter.OwnUploadViewHolder>(OwnPostDiffUtil()) {

    class OwnUploadViewHolder(val binding: OwnUploadItemBinding): RecyclerView.ViewHolder(binding.root){
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
            fun from(parent: ViewGroup): OwnUploadViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OwnUploadItemBinding.inflate(layoutInflater, parent, false)
                return OwnUploadViewHolder(binding)
            }
        }
    }

    class OwnPostDiffUtil: DiffUtil.ItemCallback<Blog>(){
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
           return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnUploadViewHolder {
        return OwnUploadViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OwnUploadViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post, user)
    }
}