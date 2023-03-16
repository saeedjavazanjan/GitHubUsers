package com.example.owlestictask.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.owlestictask.R
import com.example.owlestictask.databinding.FavUserRowBinding
import com.example.owlestictask.databinding.UserRowBinding
import com.example.owlestictask.model.User
import com.example.owlestictask.model.UserDetail
import com.example.owlestictask.presentation.user_detail.UserDetailActivity

class FavoritesAdapter(var favList:List<UserDetail>):RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemBinding =
            FavUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesAdapter.FavoriteViewHolder(itemBinding)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.binder(favList[position])
    }

    override fun getItemCount(): Int {
        return favList.size
    }





    class FavoriteViewHolder(private val binding:FavUserRowBinding):RecyclerView.ViewHolder(binding.root){


        fun binder(user:UserDetail){

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

                followersCounter.text=user.followers.toString()
                followingCounter.text=user.following.toString()
                repositoryCounter.text=user.publicRepos.toString()
                if(user.company!=null){
                    company.text=user.company

                }else{
                    company.text="---"
                }
                if(user.company!=null){
                    location.text=user.location

                }else{
                    location.text="---"
                }
            }


        }

    }


}