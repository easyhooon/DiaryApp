package com.example.diaryapp.util

// TODO out keyword 에 대해 학습
// TODO nothing keyword 에 대해 학습
sealed class RequestState<out T> {
    object Idle : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val error: Throwable) : RequestState<Nothing>()
}