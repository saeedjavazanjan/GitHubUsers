package com.example.owlestictask.presentation.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.owlestictask.adapter.ItemsLoadStateAdapter
import com.example.owlestictask.adapter.UsersAdapter
import com.example.owlestictask.databinding.ActivityMainBinding
import com.example.owlestictask.presentation.favorits.FavoritesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root





        var adapter=UsersAdapter()
        viewModel.users.observe(this){
            adapter.submitData(lifecycle=this.lifecycle,it)

        }




        binding.apply {

            userRecycler.layoutManager= LinearLayoutManager(this@MainActivity,
                RecyclerView.VERTICAL,false)
            userRecycler.adapter=adapter.withLoadStateHeaderAndFooter(
                header = ItemsLoadStateAdapter{adapter.retry() },
                footer = ItemsLoadStateAdapter{adapter.retry()}

            )


      searchBox.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
               userRecycler.scrollToPosition(0)
                    viewModel.search(query)
                   searchBox.clearFocus()

                    adapter.addLoadStateListener {loadState->

                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                          userRecycler.isVisible = false
                           emptyBoxParent.isVisible = true
                            progress.isVisible=false

                        }

                        else  if(loadState.source.refresh is LoadState.Loading) {
                            progress.isVisible=true
                            userRecycler.isVisible = false
                            emptyBoxParent.isVisible = false
                        }else if(loadState.source.refresh is LoadState.Error){
                            binding.progress.visibility= View.GONE
                            binding.userRecycler.visibility=View.GONE
                            binding.emptyBoxParent.visibility=View.VISIBLE
                            Toast.makeText(
                                this@MainActivity,
                                "cannot get the data",
                                Toast.LENGTH_SHORT
                            ).show()

                        }else{
                            progress.isVisible=false
                            userRecycler.isVisible = true
                         emptyBoxParent.isVisible = false
                        }
                    }
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

            favorite.setOnClickListener {

                val intent: Intent = Intent(this@MainActivity, FavoritesActivity::class.java)
                startActivity(intent)
            }

        }


        setContentView(view)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}

