package com.example.diaryapp.presentation.screens.write

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.diaryapp.model.Diary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    selectedDiary: Diary?,
    onDeleteConfirmed: () -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            WriteTopBar (
                selectedDiary = selectedDiary,
                onDeleteConfirmed = onDeleteConfirmed,
                onBackPressed = onBackPressed
            )
        },
        content = {

        }
    )
}