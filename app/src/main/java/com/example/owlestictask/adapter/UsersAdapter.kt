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
import com.example.owlestictask.model.User
import com.example.owlestictask.presentation.user_detail.UserDetailActivity

class UsersAdapter:PagingDataAdapter<User,UsersAdapter.UserViewHolder>(USER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding =
            UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser=getItem(position)
        if(currentUser!=null) {
            holder.binder(currentUser)
        }
    }





    class UserViewHolder(val binding: UserRowBinding) : RecyclerView.ViewHolder(binding.root) {


        fun binder(user: User) {

            binding.apply {
                username.text = user.login

               avatar.load(user.avatarUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                   placeholder(R.drawable.avatar)
                }
                root.setOnClickListener {

                    val intent: Intent = Intent(it.context, UserDetailActivity::class.java)
                    intent.putExtra("USER", user.login)
                    it.context.startActivity(intent)
                }
            }

        }

    }
    companion object{
        private val USER_COMPARATOR=object :DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User)=
                oldItem.ID==newItem.ID

            override fun areContentsTheSame(oldItem: User, newItem: User)=
                oldItem==newItem


        }
    }

}



