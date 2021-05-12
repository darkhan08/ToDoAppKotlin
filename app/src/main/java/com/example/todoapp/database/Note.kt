package com.example.todoapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "note_table")
class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,
    var titleName:String,
    var noteText:String
): Parcelable