package com.plcoding.project

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class],
    version = 1
)
abstract class BookDatabase : RoomDatabase() {

    abstract val dao: BookDao
}