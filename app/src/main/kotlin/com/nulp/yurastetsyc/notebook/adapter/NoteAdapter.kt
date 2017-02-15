package com.nulp.yurastetsyc.notebook.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nulp.yurastetsyc.notebook.R
import com.nulp.yurastetsyc.notebook.data.Note
import kotlinx.android.synthetic.main.item_note.view.*
import java.util.*

/**
 * Created by Yura Stetsyc on 2/14/17.
 */
class NoteAdapter(val mNotes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun getItemCount(): Int {
        return mNotes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_note, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        holder?.bind(mNotes[position])
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mContentText: TextView = itemView.content_text
        val mCreatedDate: TextView = itemView.created_date

        init {
            mCreatedDate.maxLines = Math.abs(Random(System.currentTimeMillis()).nextInt()) % 6 + 1
        }

        fun bind(note: Note) {
            mContentText.text = note.mContent
            mCreatedDate.text = note.mCreationDate.toString()
        }

    }

}