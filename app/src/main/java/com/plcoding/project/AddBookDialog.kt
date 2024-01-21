package com.plcoding.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddBookDialog(
    state: BookState,
    onEvent: (BookEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(BookEvent.HideDialog)
        },
        title = { Text(text = "Add Book") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(BookEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    }
                )
                TextField(
                    value = state.genre,
                    onValueChange = {
                        onEvent(BookEvent.SetGenre(it))
                    },
                    placeholder = {
                        Text(text = "Genre")
                    }
                )
                TextField(
                    value = state.author,
                    onValueChange = {
                        onEvent(BookEvent.SetAuthor(it))
                    },
                    placeholder = {
                        Text(text = "Author")
                    }
                )
                TextField(
                    value = state.rating.toString(),
                    onValueChange = {
                        val newRating = it.toDoubleOrNull()
                        if (newRating != null) {
                            onEvent(BookEvent.SetRating(newRating))
                        }
                    },
                    placeholder = {
                        Text(text = "Rating")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Checkbox(
                    checked = state.isRead,
                    onCheckedChange = { newIsRead ->
                        onEvent(BookEvent.SetIsRead(newIsRead))
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                )

                Text(
                    text = "Is read",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )


            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(BookEvent.SaveBook)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}