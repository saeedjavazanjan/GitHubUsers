package com.example.owlestictask.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class UserList {
    @SerializedName("items")
    @Expose
    var items: List<User>? = null
}