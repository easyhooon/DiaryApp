package com.example.diaryapp.presentation.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.util.Constant.APP_ID
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel : ViewModel() {

    var authenticated = mutableStateOf(false)
        private set

    var loadingState = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }

    fun signInWithMongoAtlas(
        tokenId: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    // login and create account
                    App.create(APP_ID).login(
                        Credentials.jwt(tokenId)
                        // Credentials.google(tokenId, GoogleAuthType.ID_TOKEN)
                    ).loggedIn
                }
                withContext(Dispatchers.Main) {
                    // composable function 에서 trigger 되기 때문에 Dispatchers.Main
                    onSuccess(result)
                    // consider messageBar animated time(about 300ms)
                    delay(600)
                    authenticated.value = true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // composable function 에서 trigger 되기 때문에 Dispatchers.Main
                    onError(e)
                }
            }
        }
    }
}