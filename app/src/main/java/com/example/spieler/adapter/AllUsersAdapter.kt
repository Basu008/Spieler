package com.example.spieler.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.SingleUserItemBinding
import com.example.spieler.model.*
import com.example.spieler.repository.Repository
import com.example.spieler.ui.ProfileActivity
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.FollowViewModel
import com.example.spieler.viewmodelfactory.FollowViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllUsersAdapter(private val blogResponseBody: BlogResponseBody,
                      private val currentUserId: String,
                      private val followingDataSet: FollowingDataSet)
    :ListAdapter<User, AllUsersAdapter.AllUsersViewHolder>(AllUsersDiffUtil()){

    class AllUsersViewHolder(private val binding:SingleUserItemBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("ResourceAsColor")
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

        fun bind(user: User, blogResponseBody: BlogResponseBody, currentUserId: String, followingDataSet: FollowingDataSet){
            val repository = Repository()
            var followId = MutableLiveData<List<FollowData>>()
            if(followingDataSet.content != null)
                followId.value = followingDataSet.content.filter { it.user_id == currentUserId && it.following_id == user._id }
            binding.otherUserUsername.text = user.first_name
            var followCount = user.followers.size
            var followersCountText = "Followers : $followCount"
            binding.otherUserFollowers.text = followersCountText
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
                    followCount = user.followers.size + 1
                    followersCountText = "Followers : $followCount"
                    binding.otherUserFollowers.text = followersCountText
                    CoroutineScope(Dispatchers.Main).launch {
                        val response = repository.followUser(
                            FollowRequestBody(currentUserId, user._id)
                        )
                        followId.value = listOf(response.body()?.content!!)
                    }
                    makeButtonUnfollow()
                }
                else{
                    followCount = user.followers.size - 1
                    followersCountText = "Followers : $followCount"
                    binding.otherUserFollowers.text = followersCountText
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.unfollowUser(
                            followId.value?.get(0)?._id!!,
                            UnfollowRequestBody(user._id)
                        )
                    }
                    makeButtonFollow()
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
        holder.bind(user, blogResponseBody, currentUserId, followingDataSet)
    }
}