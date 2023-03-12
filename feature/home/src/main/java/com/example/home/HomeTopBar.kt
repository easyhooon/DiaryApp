package com.example.home

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@SuppressLint("NewApi")
@ExperimentalMaterial3Api
@Composable
internal fun HomeTopBar(
    // 리스트를 스크롤 할때 TopBar 가 사라지고 DateHeader 가 TopBar 자리에 위치하도록(Sticky Header) animation 지정
    scrollBehavior: TopAppBarScrollBehavior,
    onMenuClicked: () -> Unit,
    dateIsSelected: Boolean,
    onDateSelected: (ZonedDateTime) -> Unit,
    onDateReset: () -> Unit
) {
    val dateDialog = rememberSheetState()
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }

    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = onMenuClicked
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Hamburger Menu Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        title = {
            Text(text = "Diary")
        },
        actions = {
            if (dateIsSelected) {
                // TODO 얘는 왜 그냥 이름으로 퉁쳐도 되는거지 ()도 안붙혀도 되고
                IconButton(onClick = onDateReset) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                // TODO 람다 함수의 경우 {} 로 묶어서 호출해야함
                //IconButton(onClick = dateDialog.show()) {
                IconButton(onClick = { dateDialog.show() }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )

    CalendarDialog(
        state = dateDialog,
        selection = CalendarSelection.Date { localDate ->
            pickedDate = localDate
            onDateSelected(
                ZonedDateTime.of(
                    pickedDate,
                    LocalTime.now(),
                    ZoneId.systemDefault()
                )
            )
        },
        config = CalendarConfig(monthSelection = true, yearSelection = true)
    )
}