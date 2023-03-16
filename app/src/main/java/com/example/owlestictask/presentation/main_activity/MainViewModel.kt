package com.example.owlestictask.presentation.main_activity

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.owlestictask.model.User
import com.example.owlestictask.repository.remote.GetData
import com.example.owlestictask.repository.remote.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.logging.Filter
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getData: GetData,

    ) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)


    val users = currentQuery.switchMap { query ->
        getData.getUserList(query).cachedIn(viewModelScope)
    }


    fun search(query: String) {
        currentQuery.value = query

    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }


}