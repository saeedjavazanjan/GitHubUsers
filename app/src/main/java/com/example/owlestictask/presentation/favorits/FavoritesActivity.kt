package com.example.owlestictask.presentation.favorits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.owlestictask.adapter.FavoritesAdapter
import com.example.owlestictask.databinding.ActivityFavoritsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesActivity : AppCompatActivity() {


    var _binding: ActivityFavoritsBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        _binding= ActivityFavoritsBinding.inflate(layoutInflater)
        val view=binding.root


        val adapter = FavoritesAdapter(viewModel.list)
        binding.favoritesRecycler.adapter=adapter
        binding.favoritesRecycler.layoutManager= LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)


        binding.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        setContentView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}