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
import com.example.owlestictask.model.Following
import com.example.owlestictask.presentation.user_detail.UserDetailActivity

class FollowingAdapter: PagingDataAdapter<Following, FollowingAdapter.ItemViewHolder>(USER_COMPARATOR) {


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


        fun binder(user: Following) {

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
        private val USER_COMPARATOR=object : DiffUtil.ItemCallback<Following>(){
            override fun areItemsTheSame(oldItem: Following, newItem: Following)=
                oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: Following, newItem: Following)=
                oldItem==newItem


        }
    }

}