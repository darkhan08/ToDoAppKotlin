package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao{
    @Insert
    suspend fun insert(note:Note)
    @Update
    suspend fun update(note: Note)
    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAllNotes():LiveData<List<Note>>
    @Delete
    suspend fun delete(note: Note)
    @Query("DELETE FROM note_table")
    fun deleteAll()
    @Query("SELECT * FROM NOTE_TABLE WHERE titleName LIKE :searchQuery or noteText LIKE :searchQuery")
    fun searchDatabase(searchQuery:String): Flow<List<Note>>
}