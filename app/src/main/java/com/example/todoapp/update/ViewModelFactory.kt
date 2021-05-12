package com.example.todoapp.update

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.database.Note
import java.lang.IllegalArgumentException

class ViewModelFactory(private val application: Application, private val note: Note):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateViewModel::class.java))
            return UpdateViewModel(application,note) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}