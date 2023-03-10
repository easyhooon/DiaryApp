package com.example.diaryapp.connectivity

import kotlinx.coroutines.flow.Flow

// network Connectivity 판단
interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}