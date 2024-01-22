package com.plcoding.project

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddBookDialog(
    state: BookState,
    onEvent: (BookEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var ratingText by remember { mutableStateOf("") }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(BookEvent.HideDialog)
        },
        title = { Text(text = stringResource(R.string.add_book)) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(BookEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.title))
                    }
                )
                TextField(
                    value = state.genre,
                    onValueChange = {
                        onEvent(BookEvent.SetGenre(it))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.genre))
                    }
                )
                TextField(
                    value = state.author,
                    onValueChange = {
                        onEvent(BookEvent.SetAuthor(it))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.author))
                    }
                )
                TextField(
                    value = ratingText,
                    onValueChange = {
                        ratingText = it
                        val newRating = it.toDoubleOrNull()
                        if (newRating != null) {
                            onEvent(BookEvent.SetRating(newRating))
                        }
                    },
                    placeholder = {
                        Text(
                            text = "0.0",
                            color = if (ratingText.isEmpty()) Color.Gray else LocalContentColor.current
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Checkbox(
                        checked = state.isRead,
                        onCheckedChange = { newIsRead ->
                            onEvent(BookEvent.SetIsRead(newIsRead))
                        },
                        modifier = Modifier
                    )

                    Text(
                        text = stringResource(R.string.is_read),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }


            }
        },
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                contentAlignment = Alignment.Center,
            ) {
                Button(
                    onClick = {
                        onEvent(BookEvent.SaveBook)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        color = Color.Black
                    )
                }
            }
        }
    )
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}