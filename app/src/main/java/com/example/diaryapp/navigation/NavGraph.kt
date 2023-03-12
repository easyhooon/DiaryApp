package com.example.diaryapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.auth.navigation.authenticationRoute
import com.example.home.navigation.homeRoute
import com.example.navigation.writeRoute
import com.example.util.Screen
import com.google.accompanist.pager.ExperimentalPagerApi

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
            navigateBack = {
                //TODO popBackStack() vs navigateUp
                navController.popBackStack()
            }
        )
    }
}