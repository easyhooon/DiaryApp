package com.example.diaryapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlin.math.max


@Composable
fun Gallery(
    modifier: Modifier = Modifier,
    images: List<String>,
    imageSize: Dp = 40.dp,
    spaceBetween: Dp = 10.dp,
    imageShape: CornerBasedShape = Shapes().small
) {
    BoxWithConstraints(modifier = modifier) {
        val numberOfVisibleImages = remember {
            // store the result of a simple calculation within this variable
            // without recomposing multiple different times
            derivedStateOf {
                max(
                    a = 0,
                    // calculate number of images we have
                    // 젤 왼쪽 간격 + 젤 오른쪽 간격 -> spaceBetween,
                    // 마지막 한 칸은 더보기
                    b = this.maxWidth.div(spaceBetween + imageSize).toInt().minus(1)
                )
            }
        }
        val remainingImages = remember {
            derivedStateOf {
                images.size - numberOfVisibleImages.value
            }
        }

        Row {
            images.take(numberOfVisibleImages.value).forEach {image ->
                AsyncImage(
                    // clip -> 모양을 지정
                    modifier = Modifier
                        .clip(imageShape)
                        .size(imageSize),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Gallery Image"
                )
                Spacer(modifier = Modifier.width(spaceBetween))
            }
            if(remainingImages.value > 0) {
                LastImageOverlay(
                    imageSize = imageSize,
                    imagesShape = imageShape,
                    remainingImages = remainingImages.value,
                )
            }
        }
    }
}

@Composable
fun LastImageOverlay(
    imageSize: Dp,
    imagesShape: CornerBasedShape,
    remainingImages: Int
) {
    Box(contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .clip(imagesShape)
                .size(imageSize),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {}
        Text (
            text = "+$remainingImages",
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}