package com.example.spieler.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.SingleUserItemBinding
import com.example.spieler.model.BlogResponseBody
import com.example.spieler.model.FollowRequestBody
import com.example.spieler.model.Followers
import com.example.spieler.model.User
import com.example.spieler.repository.Repository
import com.example.spieler.ui.ProfileActivity
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.FollowViewModel
import com.example.spieler.viewmodelfactory.FollowViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllUsersAdapter(private val blogResponseBody: BlogResponseBody, private val currentUserId: String)
    :ListAdapter<User, AllUsersAdapter.AllUsersViewHolder>(AllUsersDiffUtil()){

    class AllUsersViewHolder(private val binding:SingleUserItemBinding): RecyclerView.ViewHolder(binding.root){

        private fun makeButtonFollow(){
            binding.followButton.setBackgroundResource(R.drawable.follow_bg)
            binding.followButton.setText("FOLLOW")
            binding.followButton.setTextColor(R.color.main_theme_color)
        }

        private fun makeButtonUnfollow(){
            binding.followButton.setBackgroundResource(R.drawable.unfollow_bg)
            binding.followButton.setText("UNFOLLOW")
            binding.followButton.setTextColor(Color.WHITE)
        }

        @SuppressLint("ResourceAsColor")
        fun bind(user: User, blogResponseBody: BlogResponseBody, currentUserId: String){
            val repository = Repository()
            binding.otherUserUsername.text = user.first_name
            Glide.with(binding.root.context)
                .load(user.profile_img)
                .circleCrop()
                .placeholder(R.drawable.user)
                .into(binding.otherUserDP)
            binding.userItemLayout.setOnClickListener {
                Intent(binding.root.context, ProfileActivity::class.java).also {
                    it.putExtra(Constants.USER_DATA, user)
                    it.putExtra(Constants.USER_ID, currentUserId)
                    it.putExtra(Constants.BLOG_DATA, blogResponseBody)
                    binding.root.context.startActivity(it)
                }
            }
            if(!user.followers.contains(Followers(currentUserId))){
                makeButtonFollow()
            }
            else{
                makeButtonUnfollow()
            }
            binding.followButton.setOnClickListener {
                if(binding.followButton.text == "FOLLOW"){
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.followUser(
                            FollowRequestBody(currentUserId, user._id)
                        )
                    }
                    makeButtonUnfollow()
                }
                else{

                }
            }
        }

        companion object{
            fun from(parent: ViewGroup): AllUsersViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleUserItemBinding.inflate(layoutInflater, parent, false)
                return AllUsersViewHolder(binding)
            }
        }
    }

    class AllUsersDiffUtil: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllUsersViewHolder {
        return AllUsersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AllUsersViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, blogResponseBody, currentUserId)
    }
}