package com.example.diaryapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    onMenuClicked: () -> Unit,
    navigateToWrite: () -> Unit
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                onMenuClicked = onMenuClicked
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToWrite
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "New Diary Icon")
            }
        },
        content = {

        }
    )
}