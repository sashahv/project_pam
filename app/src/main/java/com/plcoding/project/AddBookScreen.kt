package com.plcoding.project

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.plcoding.project.navigation.NavigationDestination
import com.plcoding.project.ui.theme.LightBlue
import com.plcoding.project.ui.theme.LightOrange

object AddBookScreenDestination : NavigationDestination {
    override val route = "book_add"
    override val titleRes = R.string.add_book
}

@Composable
fun AddBookScreen(
    state: BookState,
    context: Context,
    navController: NavHostController = NavHostController(context),
    onEvent: (BookEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold(
        modifier = Modifier.padding(16.dp)
    ) { padding ->

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        expanded = true
                    }
                ) {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                            .border(1.dp, Color.Black)
                    ) {
                        SortField.values().forEachIndexed { index, sortField ->
                            DropdownMenuItem(
                                onClick = {
                                    onEvent(BookEvent.SortBook(sortField))
                                    selectedIndex = index
                                    showToast(
                                        context,
                                        context.getString(R.string.sorted_by) + " " + getSortFieldString(
                                            sortField,
                                            context
                                        ), LightBlue
                                    )
                                    expanded = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = getSortFieldString(sortField, context)
                                )
                            }
                            if (index < SortField.values().size - 1) {
                                Divider(color = Color.Black, thickness = 1.dp)
                            }
                        }
                    }
                }
            }
            items(state.books) { book ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightOrange)
                        .border(1.dp, Color.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp)
                    ) {

                        val stringResultFromBooleanForIsReadVariable =
                            when (book.isRead) {
                                true -> R.string.already_read
                                false -> R.string.not_read
                            }

                        Text(
                            text = "${stringResource(R.string.title)}: ${book.title} / ${
                                stringResource(
                                    stringResultFromBooleanForIsReadVariable
                                )
                            }", fontSize = 20.sp, fontFamily = FontFamily.SansSerif
                        )
                        Text(
                            text = "${stringResource(R.string.genre)}: ${book.genre}",
                            fontSize = 12.sp, fontFamily = FontFamily.SansSerif
                        )
                        Text(
                            text = "${stringResource(R.string.author)}: ${book.author}",
                            fontSize = 12.sp, fontFamily = FontFamily.SansSerif
                        )
                        Text(
                            text = "${stringResource(R.string.rating)}: ${book.rating} / 10.0",
                            fontSize = 12.sp, fontFamily = FontFamily.SansSerif
                        )
                    }
                    IconButton(onClick = {
                        onEvent(BookEvent.DeleteBook(book))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit book"
                        )
                    }
                    IconButton(onClick = {
                        onEvent(BookEvent.DeleteBook(book))
                        showToast(context, context.getString(R.string.book_deleted), LightBlue)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete book"
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    navController.navigate(BookScreenDestination.route)
                },
                modifier = Modifier
                    .padding(16.dp) // Adjust padding as needed
            ) {
                Icon(
                    imageVector = Icons.Default.Add, // Use your own icon resource
                    contentDescription = stringResource(R.string.add_book),
                    modifier = Modifier.size(18.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.add_book), color = Color.Black)
            }
        }
    }
}