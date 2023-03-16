package com.example.owlestictask.repository.remote

import com.example.owlestictask.common.Common
import com.example.owlestictask.model.*
import retrofit2.http.*


interface ApiService {
    @GET("/search/users")
   suspend fun getUserList(
        @Query("q") filter: String?, @Query("per_page") perPage:Int,
        @Query("page") currentPage:Int):UserList?


    @Headers("Authorization: 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")
    @GET("/users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username:String, @Query("per_page") perPage:Int,
        @Query("page") currentPage:Int
    ): List<Follower>?

    @GET("/users/{username}")
    suspend fun getUserDetail(
        @Path("username") username:String
    ): UserDetail?

    @Headers("Authorization: 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")
    @GET("/users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username:String, @Query("per_page") perPage:Int,
        @Query("page") currentPage:Int
    ): List<Following>?



}