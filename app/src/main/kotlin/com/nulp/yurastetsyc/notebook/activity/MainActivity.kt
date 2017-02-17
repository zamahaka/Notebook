package com.nulp.yurastetsyc.notebook.activity

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import com.nulp.yurastetsyc.notebook.R
import com.nulp.yurastetsyc.notebook.adapter.NoteAdapter
import com.nulp.yurastetsyc.notebook.data.Note
import com.nulp.yurastetsyc.notebook.database.DataBaseHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val mNotes: MutableList<Note> = mutableListOf()
    var mDataBase: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        deleteDatabase(DataBaseHelper.DATABASE_NAME)

        mDataBase = DataBaseHelper(this).writableDatabase

        initViews()
        mDataBase?.let {
            mNotes.addAll(extractItems(it))
            if (mNotes.size > 0) {
                mRecyclerView.adapter.notifyItemRangeInserted(0, mNotes.size - 1)
            }
        }
    }

    private fun extractItems(database: SQLiteDatabase): Collection<Note> {
        val cursor = database.query(DataBaseHelper.TABLE_NOTES,
                null, null, null, null, null, DataBaseHelper.NOTES_ID)

        val notes: MutableList<Note> = mutableListOf()

        if (cursor.moveToFirst()) {

            val idIndex = cursor.getColumnIndex(DataBaseHelper.NOTES_ID)
            val timeIndex = cursor.getColumnIndex(DataBaseHelper.NOTES_TIME)
            val contentIndex = cursor.getColumnIndex(DataBaseHelper.NOTES_CONTENT)
            val priorityIndex = cursor.getColumnIndex(DataBaseHelper.NOTES_PRIORITY)
            val bgIndex = cursor.getColumnIndex(DataBaseHelper.NOTES_BACKGROUND)

            do {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = cursor.getLong(timeIndex)
                notes.add(Note(
                        cursor.getLong(idIndex),
                        calendar,
                        cursor.getString(contentIndex),
                        Note.Priority.valueOf(cursor.getString(priorityIndex)),
                        cursor.getString(bgIndex)
                ))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return notes
    }

    private fun initViews() {
        val recyclerView = mRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = /*StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)*/LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = NoteAdapter(mNotes, {
            startEditNoteActivity(it)
        })

        this.fab.setOnClickListener {
            mDataBase?.let { createNewNote(it) }?.let { startEditNoteActivity(it) }
        }
    }

    private fun startEditNoteActivity(note: Note) {
        startActivityForResult(Intent(this, EditNoteActivity::class.java)
                .putExtra(EditNoteActivity.NOTE_KEY, note),
                EditNoteActivity.RQC_EDIT_NOTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EditNoteActivity.RQC_EDIT_NOTE) {
            val note: Note? = data?.extras?.get(EditNoteActivity.NOTE_KEY) as Note?
            note?.let {
                when (resultCode) {
                    EditNoteActivity.RSC_UPDATE_NOTE -> {
                        val id = it.mId
                        val index = mNotes.indexOfFirst { it.mId == id }
                        if (index == -1) {
                            mNotes.add(it)
                            mRecyclerView.adapter.notifyItemInserted(mNotes.size - 1)
                        } else {
                            mNotes[index] = it
                            mRecyclerView.adapter.notifyItemChanged(index)
                        }
                        updateNoteInDatabase(it)
                    }

                    EditNoteActivity.RSC_DELETE_NOTE -> {
                        val id = it.mId
                        val index = mNotes.indexOfFirst { it.mId == id }
                        if (index > -1) {
                            mNotes.removeAt(index)
                            mRecyclerView.adapter.notifyItemRemoved(index)
                        }
                        deleteNoteFromDatabase(it)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createNewNote(database: SQLiteDatabase): Note {
        val cv = ContentValues()

        val colorWhite: String = "#FFFFFFFF"

        cv.put(DataBaseHelper.NOTES_CONTENT, "")
        cv.put(DataBaseHelper.NOTES_TIME, System.currentTimeMillis())
        cv.put(DataBaseHelper.NOTES_PRIORITY, Note.Priority.NORMAL.name)
        cv.put(DataBaseHelper.NOTES_BACKGROUND, colorWhite)

        val id = database.insert(DataBaseHelper.TABLE_NOTES, null, cv)

        return Note(id, Calendar.getInstance(), "", Note.Priority.NORMAL, colorWhite)
    }

    private fun updateNoteInDatabase(note: Note) {
        val cv = ContentValues()

        cv.put(DataBaseHelper.NOTES_CONTENT, note.mContent)
        cv.put(DataBaseHelper.NOTES_PRIORITY, note.mPriority.name)
        cv.put(DataBaseHelper.NOTES_BACKGROUND, note.mBackGround)

        mDataBase?.update(DataBaseHelper.TABLE_NOTES, cv, "${DataBaseHelper.NOTES_ID} = ${note.mId}", null)
    }

    private fun deleteNoteFromDatabase(note: Note) {
        mDataBase?.delete(DataBaseHelper.TABLE_NOTES, "${DataBaseHelper.NOTES_ID} = ${note.mId}", null)
    }
}
