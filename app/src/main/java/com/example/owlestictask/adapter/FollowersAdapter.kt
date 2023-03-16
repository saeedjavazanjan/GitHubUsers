package com.example.owlestictask.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.owlestictask.R
import com.example.owlestictask.databinding.UserRowBinding
import com.example.owlestictask.model.Follower
import com.example.owlestictask.presentation.user_detail.UserDetailActivity

class FollowersAdapter: PagingDataAdapter<Follower, FollowersAdapter.ItemViewHolder>(USER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding =
            UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentUser=getItem(position)
        if(currentUser!=null) {
            holder.binder(currentUser)
        }
    }





    class ItemViewHolder(val binding: UserRowBinding) : RecyclerView.ViewHolder(binding.root) {


        fun binder(user: Follower) {

            binding.apply {
                username.text = user.login

                avatar.load(user.avatarUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.avatar)
                }
                binding.root.setOnClickListener {
                    val intent: Intent = Intent(it.context, UserDetailActivity::class.java)
                    intent.putExtra("USER", user.login)
                    it.context.startActivity(intent)
                }

            }

        }

    }
    companion object{
        private val USER_COMPARATOR=object : DiffUtil.ItemCallback<Follower>(){
            override fun areItemsTheSame(oldItem: Follower, newItem: Follower)=
                oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: Follower, newItem: Follower)=
                oldItem==newItem


        }
    }

}