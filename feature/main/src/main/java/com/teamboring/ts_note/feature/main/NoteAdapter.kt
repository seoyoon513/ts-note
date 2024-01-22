package com.teamboring.ts_note.feature.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamboring.ts_note.core.room.Note

class NoteAdapter(private val noteList: MutableList<Note> = mutableListOf()) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleView: TextView = view.findViewById(R.id.note_title)
        private val contentView: TextView = view.findViewById(R.id.note_content)
        private val dateView: TextView = view.findViewById(R.id.note_date)

        fun bind(note: Note) {
            titleView.text = note.title
            contentView.text = note.content
            dateView.text = note.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(notes: List<Note>?) {
        noteList.clear()
        notes?.let { noteList.addAll(it) }
        notifyDataSetChanged()
    }
}