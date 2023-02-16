package com.example.diaryapp.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.data.repository.Diaries
import com.example.diaryapp.data.repository.MongoDB
import com.example.diaryapp.util.RequestState
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {

    var diaries: MutableState<Diaries> = mutableStateOf(RequestState.Idle)

    init {
        observeAllDiaries()
    }

    private fun observeAllDiaries() {
        viewModelScope.launch {
            MongoDB.getAllDiaries().collect { result ->
                Timber.d("HomeViewModel", "$result")
                diaries.value = result
            }
        }
    }
}