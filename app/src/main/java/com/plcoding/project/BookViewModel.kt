package com.plcoding.project

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.project.ui.theme.LightBlue
import com.plcoding.project.ui.theme.WarningRed
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
    private val dao: BookDao,
    private val context: Context
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

            BookEvent.SaveBook -> {
                val title = state.value.title
                val genre = state.value.genre
                val author = state.value.author
                val rating = state.value.rating
                val isRead = state.value.isRead

                if (title.isBlank()){
                    showToast(context, context.getString(R.string.blank_title), WarningRed, false)
                    return;
                } else if (rating.isNaN() || rating < 1 || rating > 10){
                    showToast(context, context.getString(R.string.invalid_rating), WarningRed, false)
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
                    title = "",
                    genre = "",
                    author = "",
                    rating = 0.0,
                    isRead = false
                ) }
                showToast(context, context.getString(R.string.book_added), LightBlue, false)
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

            is BookEvent.SortBook -> {
                _sortField.value = event.sortField
            }
        }
    }
}