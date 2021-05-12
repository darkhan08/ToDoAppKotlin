package com.example.todoapp.update

import android.app.Application
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.Note
import com.example.todoapp.database.NoteDatabase
import com.example.todoapp.database.UserRepository
import kotlinx.coroutines.launch


class UpdateViewModel(application: Application, note: Note):AndroidViewModel(application){
    private val repository: UserRepository
    private val _selectedProperty =MutableLiveData<Note>()
    val selectedProperty:LiveData<Note>
        get() = _selectedProperty

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDatabaseDao
        repository = UserRepository(noteDao)
        _selectedProperty.value = note
    }

    fun update(note:Note){
        viewModelScope.launch {
            repository.Update(note)
        }
    }
    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    fun delete(note: Note){
        viewModelScope.launch {
            repository.Delete(note)
        }
    }

}