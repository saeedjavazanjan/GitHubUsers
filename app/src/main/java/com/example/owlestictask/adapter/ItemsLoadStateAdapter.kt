package com.example.owlestictask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.owlestictask.databinding.LoadingRowBinding

class ItemsLoadStateAdapter(private val retry:()->Unit):LoadStateAdapter<ItemsLoadStateAdapter.LoadStateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding=LoadingRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)

    }




    inner class LoadStateViewHolder(private val binding: LoadingRowBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState){
            binding.apply {
                progressLoading.isVisible=loadState is LoadState.Loading
                message.isVisible=loadState !is LoadState.Loading
                retryButton.isVisible=loadState !is LoadState.Loading
            }

        }

    }


}