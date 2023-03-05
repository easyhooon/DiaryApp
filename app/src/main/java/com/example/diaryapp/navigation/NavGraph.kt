package com.example.diaryapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.diaryapp.R
import com.example.diaryapp.model.Diary
import com.example.diaryapp.presentation.components.DisplayAlertDialog
import com.example.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.example.diaryapp.presentation.screens.auth.AuthenticationViewModel
import com.example.diaryapp.presentation.screens.home.HomeScreen
import com.example.diaryapp.presentation.screens.home.HomeViewModel
import com.example.diaryapp.presentation.screens.write.WriteScreen
import com.example.diaryapp.presentation.screens.write.WriteViewModel
import com.example.diaryapp.util.Constant.APP_ID
import com.example.diaryapp.util.Constant.KEY_DIARY_ID
import com.example.diaryapp.util.RequestState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    onDateLoaded: () -> Unit
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                // home 으로 이동 할때 이전에 쌓여진 스택을 제거
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onDateLoaded = onDateLoaded
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screen.Write.route)
            },
            navigateToWriteArgs = {
                navController.navigate(Screen.Write.passDiaryId(diaryId = it))
            },
            navigateToAuth = {
                // Authentication 으로 이동 할때 이전에 쌓여진 스택을 제거
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            },
            onDateLoaded = onDateLoaded
        )
        writeRoute(
            onBackPressed = {
                //TODO popBackStack() vs navigateUp
                navController.popBackStack()
            }
        )
    }
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
    onDateLoaded: () -> Unit
) {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState

        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        // trigger only once
        LaunchedEffect(key1 = Unit) {
            onDateLoaded()
        }

        AuthenticationScreen(
            authenticated = authenticated,
            // google login dialog open state
            // loadingState = oneTapState.opened,
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                // google login dialog open
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        viewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToHome = navigateToHome
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToWriteArgs: (String) -> Unit,
    navigateToAuth: () -> Unit,
    onDateLoaded: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val viewModel: HomeViewModel = viewModel()
        // val diaries = viewModel.diaries
        // TODO by 로 선언해야 에러가 나지 않는 이유 학습
        val diaries by viewModel.diaries
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        // we can remember this value across multiple recompositions
        var signOutDialogOpened by remember { mutableStateOf(false) }

        // SplashScreen  후에 빈 스크린이 나타나는 것을 막기 위함
        // 데이터를 다 받아오면 splashScreen 이 종료되도록 설정
        LaunchedEffect(key1 = diaries) {
            if (diaries !is RequestState.Loading) {
                onDateLoaded()
            }
        }

        HomeScreen(
            diaries = diaries,
            drawerState = drawerState,
            onMenuClicked = {
                scope.launch {
                    // suspend function()
                    drawerState.open()
                }
            },
            onSignOutClicked = {
                signOutDialogOpened = true
            },
            navigateToWrite = navigateToWrite,
            navigateToWriteWithArgs = navigateToWriteArgs
        )

        DisplayAlertDialog(
            title = stringResource(id = R.string.sign_out),
            message = stringResource(id = R.string.sign_out_message),
            dialogOpened = signOutDialogOpened,
            onDialogClosed = {
                signOutDialogOpened = false
            },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    // logOut() -> suspend function
                    val user = App.create(APP_ID).currentUser
                    if (user != null) {
                        user.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }
                    }
                }
            }
        )
    }
}

@ExperimentalPagerApi
@ExperimentalFoundationApi
fun NavGraphBuilder.writeRoute(onBackPressed: () -> Unit) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = KEY_DIARY_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        val viewModel: WriteViewModel = viewModel()
        val uiState = viewModel.uiState
        val pagerState = rememberPagerState()

        LaunchedEffect(key1 = uiState) {
            Timber.d("SelectedDiary", "${uiState.selectedDiaryId}")
        }

        WriteScreen(
            uiState = uiState,
            selectedDiary = Diary().apply {
                title = "Title"
                description = "Some Random Text"
            },
            pagerState = pagerState,
            onTitleChanged = { viewModel.setTitle(title = it) },
            onDescriptionChanged = { viewModel.setDescription(description = it) },
            onDeleteConfirmed = {},
            onBackPressed = onBackPressed
        )
    }
}