package com.example.todoapp.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.database.Note
import com.example.todoapp.database.NoteDatabase
import com.example.todoapp.database.UserRepository
class ViewModel(application: Application) :
    AndroidViewModel(application) {
    val readAllData: LiveData<List<Note>>
    private val repository: UserRepository

    private val _navigateToAdd=MutableLiveData<Boolean>()
    val navigateToAdd:LiveData<Boolean>
        get() = _navigateToAdd

    private var _navigationToUpdate = MutableLiveData<Note>()
    val navigationToUpdate:LiveData<Note>
        get() = _navigationToUpdate


    init {
        val noteDao = NoteDatabase.getInstance(application).noteDatabaseDao
        repository = UserRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun navigatedToAdd(){
        _navigateToAdd.value = null
    }

    fun startNavigateToAdd(){
        _navigateToAdd.value = true
    }

    fun searchDatabase(searchQuery:String):LiveData<List<Note>>{
        return repository.SearchDatabase(searchQuery).asLiveData()
    }

    fun deleteAllNotes(){
        repository.DeleteAll()
    }

    fun onNoteClicked(note:Note){
        _navigationToUpdate.value = note
    }
    fun onUpdateNavigated(){
        _navigationToUpdate.value = null
    }
}