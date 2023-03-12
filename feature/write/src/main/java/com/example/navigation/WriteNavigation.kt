package com.example.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.util.Constant.KEY_DIARY_ID
import com.example.util.Screen
import com.example.util.model.Mood
import com.example.write.WriteScreen
import com.example.write.WriteViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@ExperimentalFoundationApi
fun NavGraphBuilder.writeRoute(navigateBack: () -> Unit) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = KEY_DIARY_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        val viewModel: WriteViewModel = hiltViewModel()
        val context = LocalContext.current
        val uiState = viewModel.uiState
        val pagerState = rememberPagerState()
        val galleryState = viewModel.galleryState
        // derivedStateOf 를 사용하여 recomposition 을 최소화
        val pageNumber by remember { derivedStateOf { pagerState.currentPage } }

//        LaunchedEffect(key1 = uiState) {
//            Timber.d("SelectedDiary", "${uiState.selectedDiaryId}")
//        }

        WriteScreen(
            uiState = uiState,
            pagerState = pagerState,
            galleryState = galleryState,
            moodName = { Mood.values()[pageNumber].name },
            onTitleChanged = { viewModel.setTitle(title = it) },
            onDescriptionChanged = { viewModel.setDescription(description = it) },
            onDateTimeUpdate = { viewModel.updateDateTime(zonedDateTime = it) },
            onBackPressed = navigateBack,
            onDeleteConfirmed = {
                viewModel.deleteDiary(
                    onSuccess = {
                        android.widget.Toast.makeText(context, "Deleted", android.widget.Toast.LENGTH_SHORT).show()
                        navigateBack()
                    },
                    onError = { message ->
                        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
                    })
            },
            onSaveClicked = {
                viewModel.updateAndRegisterDiary(
                    diary = it.apply { mood = Mood.values()[pageNumber].name },
                    onSuccess = navigateBack,
                    onError = { message ->
                        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onImageSelect = {
                val type = context.contentResolver.getType(it)?.split("/")?.last() ?: "jpg"
                viewModel.addImage(image = it, imageType = type)
            },
            onImageDeleteClicked = { galleryState.removeImage(it) }
        )
    }
}