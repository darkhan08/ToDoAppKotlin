package com.example.todoapp.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.database.Note
import com.example.todoapp.databinding.ItemViewBinding

class NoteAdapter(val clickListner:NoteListner) : ListAdapter<Note, NoteAdapter.ViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            clickListner.onClick(item)
        }
        holder.bind(item)
    }


    class ViewHolder private constructor(val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.note = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater)
                return ViewHolder(binding)
            }
        }
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}

class NoteListner(val clickListener: (note: Note) -> Unit) {
    fun onClick(note:Note) = clickListener(note)
}