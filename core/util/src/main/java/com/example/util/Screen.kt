package com.example.util

import com.example.util.Constant.KEY_DIARY_ID

sealed class Screen(val route: String) {
    object Authentication: Screen(route = "authentication_screen")
    object Home: Screen(route = "home_screen")
    // ? <- argument is optional, key={value} 의 형태로 parameter 전달
    object Write: Screen(route ="write_screen?$KEY_DIARY_ID={$KEY_DIARY_ID}") {
        fun passDiaryId(diaryId: String) =
            "write_screen?$KEY_DIARY_ID=$diaryId"
    }
}
