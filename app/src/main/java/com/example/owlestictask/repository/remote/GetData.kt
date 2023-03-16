package com.example.owlestictask.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.owlestictask.model.UserDetail
import com.example.owlestictask.utils.FollowerPagingSource
import com.example.owlestictask.utils.FollowingPagingSource
import com.example.owlestictask.utils.UserPagingSource

class GetData(
    private val service: ApiService

) {
    fun getUserList(filter:String)=Pager(
        config = PagingConfig(
            pageSize = 30,
            maxSize = 1000,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {UserPagingSource(service,filter)}
            , initialKey = 1
    ).liveData



    fun getUserFollowers(userName: String)=Pager(
    config = PagingConfig(
    pageSize = 30,
    maxSize = 100,
    enablePlaceholders = false
    ),
    pagingSourceFactory = {FollowerPagingSource(service,userName)}
    , initialKey = 1
    ).liveData

    fun getUserFollowing(userName: String)=Pager(
        config = PagingConfig(
            pageSize = 30,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {FollowingPagingSource(service,userName)}
        , initialKey = 1
    ).liveData

    suspend fun getUserDetail(username:String):UserDetail{
        return service.getUserDetail(username)!!
    }
}