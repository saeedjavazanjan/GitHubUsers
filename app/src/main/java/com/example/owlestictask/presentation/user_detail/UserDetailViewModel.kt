package com.example.owlestictask.presentation.user_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.owlestictask.model.UserDetail
import com.example.owlestictask.repository.local.AppDatabase
import com.example.owlestictask.repository.local.DatabaseHelper
import com.example.owlestictask.repository.remote.GetData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject constructor(
    private val getData: GetData,
    private val db: AppDatabase
) : ViewModel() {

    val databaseHelper = DatabaseHelper(db)

    private val currentFollowerQuery = MutableLiveData(DEFAULT_FOLLOWER_QUERY)
    private val currentFollowingQuery = MutableLiveData(DEFAULT_FOLLOWING_QUERY)

//get followers data
    val follower = currentFollowerQuery.switchMap { query ->
        getData.getUserFollowers(query).cachedIn(viewModelScope)
    }
    fun provideFollowers(query: String) {
        currentFollowerQuery.value = query

    }



    //get following data
    val following = currentFollowingQuery.switchMap { query ->
        getData.getUserFollowing(query).cachedIn(viewModelScope)
    }
    fun provideFollowing(query: String) {
        currentFollowingQuery.value = query

    }


    //get userDetail object
    suspend fun getUserDetail(username: String): UserDetail {
        return withContext(Dispatchers.IO) {
            getData.getUserDetail(username)

        }


    }


     suspend fun checkExist(userId: Int): Boolean {
         return withContext(Dispatchers.IO) {
             try {
                 databaseHelper.checkExist(userId)
             } catch (_: java.lang.Exception) {
                 false
             }
         }
    }

     fun deleteFromFavorites(userId:Int){
        viewModelScope.launch (Dispatchers.Default){

            try {
                databaseHelper.delete(userId)

            }catch (_:Exception){

            }
        }
    }

    //save user in favorite list
    suspend fun saveAsFav(user: UserDetail): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                databaseHelper.insert(user)
                true
            } catch (_: java.lang.Exception) {
                false
            }
        }
    }


    companion object {
        private const val DEFAULT_FOLLOWER_QUERY = ""
        private const val DEFAULT_FOLLOWING_QUERY = ""

    }


}