package com.plcoding.project

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BookViewModel(
    private val dao: BookDao
) : ViewModel() {

    private val _sortField = MutableStateFlow(SortField.TITLE)
    private val _books = _sortField
        .flatMapLatest { sortField ->
            when (sortField) {
                SortField.TITLE -> dao.getBooksOrderedByTitle()
                SortField.GENRE -> dao.getBooksOrderedByGenre()
                SortField.AUTHOR -> dao.getBooksOrderedByAuthor()
                SortField.RATING -> dao.getBooksOrderedByRating()
                SortField.IS_READ -> dao.getBooksOrderedByIsRead()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(BookState())

    val state = combine(_state, _sortField, _books) { state, sortField, books ->
        state.copy(
            books = books,
            sortField = sortField
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BookState())

    fun onEvent(event: BookEvent) {
        when (event) {
            is BookEvent.DeleteBook -> {
                viewModelScope.launch {
                    dao.deleteBook(event.book)
                }
            }

            BookEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingBook = false
                    )
                }
            }

            BookEvent.SaveBook -> {
                val title = state.value.title
                val genre = state.value.genre
                val author = state.value.author
                val rating = state.value.rating
                val isRead = state.value.isRead

                if (title.isBlank() || rating.isNaN() || rating < 0 || rating > 10){
                    return;
                }

                val book = Book(
                    title,
                    genre,
                    author,
                    rating,
                    isRead
                )

                viewModelScope.launch {
                    dao.upsertBook(book)
                }

                _state.update { it.copy(
                    isAddingBook = false,
                    title = "",
                    genre = "",
                    author = "",
                    rating = 0.0,
                    isRead = false
                ) }
            }

            is BookEvent.SetTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            is BookEvent.SetAuthor -> {
                _state.update {
                    it.copy(
                        author = event.author
                    )
                }
            }

            is BookEvent.SetGenre -> {
                _state.update {
                    it.copy(
                        genre = event.genre
                    )
                }
            }

            is BookEvent.SetRating -> {
                _state.update {
                    it.copy(
                        rating = event.rating
                    )
                }
            }

            is BookEvent.SetIsRead -> {
                _state.update {
                    it.copy(
                        isRead = event.isRead
                    )
                }
            }

            BookEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingBook = true
                    )
                }
            }

            is BookEvent.SortBook -> {
                _sortField.value = event.sortField
            }
        }
    }
}