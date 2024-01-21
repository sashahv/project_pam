package com.plcoding.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.roomguideandroid.R
import java.util.Locale

@Composable
fun BookScreen(
    state: BookState,
//    navigateToItemEntry: () -> Unit,
    onEvent: (BookEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
//                onClick = navigateToItemEntry,
//                shape = MaterialTheme.shapes.medium,
//                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
                onClick = {
                    onEvent(BookEvent.ShowDialog)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_book)
                )
            }
        },
        modifier = Modifier.padding(16.dp)
    ) { padding ->
        if(state.isAddingBook){
            AddBookDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SortField.values().forEach {
                            sortField ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(BookEvent.SortBook(sortField))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            RadioButton(
                                selected = state.sortField == sortField,
                                onClick = {
                                    onEvent(BookEvent.SortBook(sortField))
                                }
                            )
                            val sortFieldString = when(sortField) {
                                SortField.TITLE -> R.string.title
                                SortField.GENRE -> R.string.genre
                                SortField.AUTHOR -> R.string.author
                                SortField.RATING -> R.string.rating
                                SortField.IS_READ -> R.string.is_read
                            }
                            Text(text = stringResource(sortFieldString))
                        }
                    }
                }
            }
            items(state.books){ book ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        val stringResultFromBooleanForIsReadVariable = when(book.isRead){
                            true -> R.string.already_read
                            false -> R.string.not_read
                        }

                        Text(
                            text =  "${stringResource(R.string.title)}: ${book.title} / ${stringResource(
                                stringResultFromBooleanForIsReadVariable
                            )}", fontSize = 20.sp, fontFamily = FontFamily.SansSerif
                        )
                        Text(
                            text =  "${stringResource(R.string.genre)}: ${book.genre}",
                            fontSize = 12.sp, fontFamily = FontFamily.SansSerif
                        )
                        Text(
                            text =  "${stringResource(R.string.author)}: ${book.author}",
                            fontSize = 12.sp, fontFamily = FontFamily.SansSerif
                        )
                        Text(
                            text =  "${stringResource(R.string.rating)}: ${book.rating} / 10",
                            fontSize = 12.sp, fontFamily = FontFamily.SansSerif
                        )
                    }
                    IconButton(onClick = {
                        onEvent(BookEvent.DeleteBook(book))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete book"
                        )
                    }
                }
            }
        }
    }
}