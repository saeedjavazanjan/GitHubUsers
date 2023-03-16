package com.example.owlestictask.presentation.user_detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.owlestictask.R
import com.example.owlestictask.adapter.FollowersAdapter
import com.example.owlestictask.adapter.FollowingAdapter
import com.example.owlestictask.databinding.ActivityUserDetailBinding
import com.example.owlestictask.presentation.favorits.FavoritesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    var _binding: ActivityUserDetailBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: UserDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root

        val userName = intent.getStringExtra("USER")

        val followersAdapter = FollowersAdapter()
        val followingAdapter = FollowingAdapter()



        binding.apply {
            //observe the data
            viewModel.follower.observe(this@UserDetailActivity) {
                followersAdapter.submitData(this@UserDetailActivity.lifecycle, it)
            }

            viewModel.following.observe(this@UserDetailActivity) {

                followingAdapter.submitData(this@UserDetailActivity.lifecycle, it)
            }


            //custom tabLayout master
            binding.followersTab.setOnClickListener {
                peopleRecycler.scrollToPosition(0)

                binding.followersTab.background =
                    ContextCompat.getDrawable(
                        this@UserDetailActivity,
                        R.drawable.tab_selected_background
                    )
                binding.followersTab.setTextColor(
                    ContextCompat.getColor(
                        this@UserDetailActivity,
                        R.color.primary
                    )
                );
                binding.followingTab.background =
                    ContextCompat.getDrawable(
                        this@UserDetailActivity,
                        R.drawable.tab_deselected_background
                    )
                binding.followingTab.setTextColor(
                    ContextCompat.getColor(
                        this@UserDetailActivity,
                        R.color.white
                    )
                );

                viewModel.provideFollowers(userName.toString())
                binding.peopleRecycler.adapter = followersAdapter

            }

            binding.followingTab.setOnClickListener {
                lifecycleScope.launch {
                    peopleRecycler.scrollToPosition(0)
                    binding.followingTab.background =
                        ContextCompat.getDrawable(
                            this@UserDetailActivity,
                            R.drawable.tab_selected_background
                        )
                    binding.followingTab.setTextColor(
                        ContextCompat.getColor(
                            this@UserDetailActivity,
                            R.color.primary
                        )
                    );
                    binding.followersTab.background =
                        ContextCompat.getDrawable(
                            this@UserDetailActivity,
                            R.drawable.tab_deselected_background
                        )
                    binding.followersTab.setTextColor(
                        ContextCompat.getColor(
                            this@UserDetailActivity,
                            R.color.white
                        )
                    );

                    viewModel.provideFollowing(userName.toString())

                    binding.peopleRecycler.adapter = followingAdapter


                }

            }




            //get user detail object and set to UI
            lifecycleScope.launch {
                val userDetail = viewModel.getUserDetail(userName.toString())
                repositoryCounter.text = userDetail.publicRepos.toString()
                followersCounter.text = userDetail.followers.toString()
                followingCounter.text = userDetail.following.toString()
                username.text = userDetail.login.toString()
                if( userDetail.bio !=null) {
                    bio.text = userDetail.bio.toString()
                }
                title.text = userDetail.login.toString()
                avatar.load(userDetail.avatarUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.avatar)
                }

                //favorite manage
                fav.setOnClickListener {
                    lifecycleScope.launch {
                        if(viewModel.checkExist(userDetail.userId!!)){
                            viewModel.deleteFromFavorites(userDetail.userId!!)
                            Toast.makeText(
                                this@UserDetailActivity,
                                "User removed from your favorite list",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else {
                            if (viewModel.saveAsFav(userDetail)) {
                                fav.setImageResource(R.drawable.favorite);

                                Toast.makeText(
                                    this@UserDetailActivity,
                                    "User saved in your favorite list",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@UserDetailActivity,
                                    "ERROR...",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                        }
                    }
                }

                back.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                favorite.setOnClickListener {

                    val intent: Intent = Intent(this@UserDetailActivity, FavoritesActivity::class.java)
                    startActivity(intent)
                }




                //set adapter to recyclerview
                viewModel.provideFollowers(userName.toString())
                viewModel.provideFollowing(userName.toString())
                binding.peopleRecycler.adapter = followersAdapter
                peopleRecycler.layoutManager = LinearLayoutManager(
                    this@UserDetailActivity,
                    RecyclerView.VERTICAL, false
                )
                peopleRecycler.layoutManager = LinearLayoutManager(
                    this@UserDetailActivity,
                    RecyclerView.VERTICAL, false
                )
            }


        }

        //load state handling for followers
        lifecycleScope.launch() {
            followersAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Error -> {
                        Toast.makeText(
                            this@UserDetailActivity,
                            "api limited pleas try later",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    else -> {


                    }
                }

            }

        }



        setContentView(view)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }


}
