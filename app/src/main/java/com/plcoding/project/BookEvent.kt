package com.plcoding.project

sealed interface BookEvent {
    object SaveBook: BookEvent
    data class SetTitle(val title: String): BookEvent
    data class SetGenre(val genre: String): BookEvent
    data class SetAuthor(val author: String): BookEvent
    data class SetRating(val rating: Double): BookEvent
    data class SetIsRead(val isRead: Boolean): BookEvent
    object ShowDialog: BookEvent
    object HideDialog: BookEvent
    data class SortBook(val sortField: SortField): BookEvent
    data class DeleteBook(val book: Book): BookEvent
}