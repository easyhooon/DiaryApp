package com.example.diaryapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import com.example.diaryapp.data.repository.Diaries
import com.example.diaryapp.util.RequestState

@ExperimentalFoundationApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    diaries: Diaries,
    drawerState: DrawerState,
    onMenuClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    navigateToWrite: () -> Unit
) {
    // val padding = remember { mutableStateOf(PaddingValues()) }
    var padding by remember { mutableStateOf(PaddingValues()) }
    // navigation Drawer 를 포함한 화면의 경우 Scaffold 를 navigation Drawer 가 포함 하는 구조
    HomeNavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    onMenuClicked = onMenuClicked
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(end = padding.calculateEndPadding(LayoutDirection.Ltr)),
                    onClick = navigateToWrite
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "New Diary Icon")
                }
            },
            content = {
                // padding values 를 지정해줘야 Top Bar 에 의해 리스트 아이템이 가려지는 것을 방지할 수 있음
                padding = it
                when (diaries) {
                    is RequestState.Success -> {
                        HomeContent(
                            paddingValues = it,
                            diaryNotes = diaries.data,
                            onClick = {}
                        )
                    }
                    is RequestState.Error -> {
                        EmptyPage(
                            title = "Error",
                            subtitle = "${diaries.error.message}"
                        )
                    }
                    is RequestState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    else -> {}
                }
            }
        )
    }
}