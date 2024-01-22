package com.plcoding.project

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.plcoding.project.navigation.NavigationDestination
import com.plcoding.project.ui.theme.WarningRed


object AddBookScreenDestination : NavigationDestination {
    override val route = "add_book"
    override val titleRes = R.string.add_book
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddBookScreen(
    state: BookState,
    navigateBack: () -> Unit,
    onEvent: (BookEvent) -> Unit
) {
    val context = LocalContext.current
    var addMoreBooks by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.add_book), color = Color.Black) },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(45.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(BookEvent.SetTitle(it))
                    },
                    label = { Text(text = stringResource(R.string.title), color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )
                TextField(
                    value = state.genre,
                    onValueChange = {
                        onEvent(BookEvent.SetGenre(it))
                    },
                    label = { Text(text = stringResource(R.string.genre), color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )
                TextField(
                    value = state.author,
                    onValueChange = {
                        onEvent(BookEvent.SetAuthor(it))
                    },
                    label = { Text(text = stringResource(R.string.author), color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )
                TextField(
                    value = if (state.rating != 0.0) state.rating.toString() else "",
                    onValueChange = {
                        if (it.toDoubleOrNull() != null) {
                            onEvent(BookEvent.SetRating(it.toDouble()))
                        } else {
                            showToast(
                                context,
                                context.getString(R.string.invalid_rating),
                                WarningRed,
                                false
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = "0.0"
                        )
                    },
                    label = { Text(text = stringResource(R.string.rating), color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Checkbox(
                        checked = addMoreBooks,
                        onCheckedChange = {
                            addMoreBooks = (!addMoreBooks)
                        },
                        modifier = Modifier
                    )

                    Text(
                        text = stringResource(R.string.add_more_books),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        onEvent(BookEvent.SaveBook)
                        if (!addMoreBooks) {
                            navigateBack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text(text = stringResource(R.string.add_book), color = Color.Black)
                }
            }
        }
    )
}
