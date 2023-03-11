package com.example.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@ExperimentalMaterial3Api
@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    dialogOpened: Boolean,
    onYesClicked: () -> Unit,
    onDialogClosed: () -> Unit,
) {
    if (dialogOpened) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        onDialogClosed()
                    }
                ) {
                    Text(text = stringResource(com.example.ui.R.string.yes))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDialogClosed) {
                    Text(text = stringResource(com.example.ui.R.string.no))
                }
            },
            onDismissRequest = onDialogClosed
        )
    }
}