package com.example.diaryapp.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diaryapp.model.Diary
import com.example.diaryapp.presentation.components.DiaryHolder
import java.time.LocalDate

@ExperimentalFoundationApi
@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    diaryNotes: Map<LocalDate, List<Diary>>,
    onClick: (String) -> Unit
) {
    if (diaryNotes.isNotEmpty()) {
        LazyColumn(
            // TODO 해당 내용 복습
            modifier = Modifier
                .padding(horizontal = 24.dp)
                // 키보드와 버튼 사이의 불필요한 간격을 없앰
                .navigationBarsPadding()
                // TopBar 의 크기 만큼 padding 을 줌
                .padding(top = paddingValues.calculateTopPadding())
                    // 가로 전환시 바텀 시스템 바에 침범 하지 않도록
                    //.padding(bottom = paddingValues.calculateBottomPadding())
                    //.padding(start = paddingValues.calculateStartPadding(LayoutDirection.Ltr))
                    //.padding(end = paddingValues.calculateEndPadding(LayoutDirection.Ltr))
        ) {
            diaryNotes.forEach { (localDate, diaries) ->
                stickyHeader(key = localDate) {
                    DateHeader(localDate = localDate)
                }
                items(
                    items = diaries,
                    // key 를 지정 하여 무분별한 recomposition 을 방지 (변경된 항목만 다시 그리도록)
                    // key accepts primitive type (string, integer)
                    // need Type Casting
                    key = { it._id.toString()}
                ) {
                    DiaryHolder(diary = it, onClick = onClick)
                }
            }
        }
    } else {
        EmptyPage()
    }
}