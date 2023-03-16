package com.example.owlestictask.repository.remote

data class ListState<T>(
    val loading:Boolean=true,
    val list: List<T> = listOf(),
    val response:String=""

    )
