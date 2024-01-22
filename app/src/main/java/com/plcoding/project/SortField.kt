package com.plcoding.project

import android.content.Context

enum class SortField {
    TITLE,
    GENRE,
    AUTHOR,
    RATING,
    IS_READ
}
fun getSortFieldString(sortField: SortField, context: Context): String {
    return when (sortField) {
        SortField.TITLE -> context.getString(R.string.title)
        SortField.GENRE -> context.getString(R.string.genre)
        SortField.AUTHOR -> context.getString(R.string.author)
        SortField.RATING -> context.getString(R.string.rating)
        SortField.IS_READ -> context.getString(R.string.is_read)
    }
}