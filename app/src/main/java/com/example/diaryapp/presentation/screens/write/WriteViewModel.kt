package com.example.diaryapp.presentation.screens.write

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.diaryapp.model.Mood
import com.example.diaryapp.util.Constant.KEY_DIARY_ID

class WriteViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    init {
        getDiaryIdArgument()
    }

    private fun getDiaryIdArgument() {
        uiState = uiState.copy(
            selectedDiaryId = savedStateHandle.get<String>(
                key = KEY_DIARY_ID
            )
        )
    }
}

data class UiState(
    val selectedDiaryId: String? = null,
    val title: String = "",
    val description: String = "",
    val mood: Mood = Mood.Neutral
)