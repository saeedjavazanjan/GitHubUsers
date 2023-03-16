package com.example.owlestictask.presentation.favorits

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.owlestictask.model.User
import com.example.owlestictask.model.UserDetail
import com.example.owlestictask.repository.local.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel
@Inject constructor(
    private val db: AppDatabase
)
    :ViewModel() {


    val list:List<UserDetail> =db.dbDao().getAll()

}