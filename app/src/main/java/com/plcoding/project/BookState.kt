package com.plcoding.project

data class BookState(
    val books: List<Book> = emptyList(),
    var title: String = "",
    var genre: String = "",
    var author: String = "",
    var rating: Double = 0.0,
    var isRead: Boolean = false,
    var sortField: SortField = SortField.TITLE,
)