package com.example.todoapp.add

import android.app.Application
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.Note
import com.example.todoapp.database.NoteDatabase
import com.example.todoapp.database.NoteDatabaseDao
import com.example.todoapp.database.UserRepository
import kotlinx.coroutines.launch

class AddViewModel(application: Application):AndroidViewModel(application) {
    private val repository: UserRepository
    init {
        val noteDao = NoteDatabase.getInstance(application).noteDatabaseDao
        repository = UserRepository(noteDao)
    }

    fun addNote(note:Note){
        viewModelScope.launch {
            repository.addNote(note)
        }
    }

     fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
}