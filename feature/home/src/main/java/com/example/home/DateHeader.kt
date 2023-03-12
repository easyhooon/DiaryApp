package com.example.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate

// 가로 화면으로 전환 후 스크롤 시 리스트가 DateHeader 밑으로 들어가는 이슈
// -> background 을 padding 이전에 지정
@SuppressLint("NewApi")
@Composable
internal fun DateHeader(localDate: LocalDate) {
    Row(
        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 14.dp)
//            .background(MaterialTheme.colorScheme.surface),
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            // desugaring library 를 통해 api level 관련 warning 이 뜨지 않음
            Text(
                // %: 명령의 시작
                // 0: 채워질 문자
                // 2: 총 자릿수
                // d: 10진수(정수)
                // -> 02, 03 이런식으로 앞에 0을 채움
                text = String.format("%02d", localDate.dayOfMonth),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
            Text(
                // get only three character
                // take 함수는 string type extension function
                text = localDate.dayOfWeek.toString().take(3),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = localDate.month.toString().lowercase()
                    .replaceFirstChar { it.titlecase() },
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
            Text(
                text = "${localDate.year}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
@Preview(showBackground = true)
internal fun DateHeaderPreview() {
    DateHeader(localDate = LocalDate.now())
}