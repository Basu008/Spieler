package com.example.spieler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.CommentItemBinding
import com.example.spieler.model.Comment

class CommentAdapter: ListAdapter<Comment, CommentAdapter.CommentViewHolder>(CommentDiffUtil()) {

    class CommentViewHolder(private val binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment){
            val context = binding.commentLayout.context
            if(comment.comment_author != null){
                binding.commentAuthorUsername.text = comment.comment_author.first_name
                binding.commentAuthorComment.text = comment.description
                if(comment.comment_author.profile_img == "default"){
                    Glide.with(context)
                        .load(R.drawable.user)
                        .circleCrop()
                        .into(binding.commentAuthorDP)
                }
                else
                    Glide.with(context)
                        .load(comment.comment_author.profile_img)
                        .circleCrop()
                        .into(binding.commentAuthorDP)
            }

        }

        companion object{
            fun from(parent: ViewGroup): CommentViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CommentItemBinding.inflate(layoutInflater, parent, false)
                return CommentViewHolder(binding)
            }
        }
    }

    class CommentDiffUtil: DiffUtil.ItemCallback<Comment>(){
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            TODO("Not yet implemented")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }
}