package com.example.todoapp.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val noteDao: NoteDatabaseDao) {
    val readAllData: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun Delete(note: Note) {
        noteDao.delete(note)
    }

    fun SearchDatabase(query: String): Flow<List<Note>> {
        return noteDao.searchDatabase(query)
    }
    fun DeleteAll(){
        noteDao.deleteAll()
    }
    suspend fun Update(note:Note){
        noteDao.update(note)
    }
}