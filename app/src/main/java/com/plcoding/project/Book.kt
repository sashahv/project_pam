package com.plcoding.project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    val title: String,
    val genre: String,
    val author: String,
    val rating: Double,
    val isRead: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
