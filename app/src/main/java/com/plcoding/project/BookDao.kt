package com.plcoding.project

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBook(book: Book): Long

    @Delete
    suspend fun deleteBook(book: Book): Int

    @Query("SELECT * FROM book ORDER BY title ASC")
    fun getBooksOrderedByTitle(): Flow<List<Book>>

    @Query("SELECT * FROM book ORDER BY genre ASC")
    fun getBooksOrderedByGenre(): Flow<List<Book>>

    @Query("SELECT * FROM book ORDER BY author ASC")
    fun getBooksOrderedByAuthor(): Flow<List<Book>>

    @Query("SELECT * FROM book ORDER BY rating ASC")
    fun getBooksOrderedByRating(): Flow<List<Book>>

    @Query("SELECT * FROM book ORDER BY isRead ASC")
    fun getBooksOrderedByIsRead(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE title = :sTitle ")
    fun getBooksByTitle(sTitle: String): Flow<List<Book>>
}