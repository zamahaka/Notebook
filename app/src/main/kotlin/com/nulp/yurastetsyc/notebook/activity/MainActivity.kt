package com.nulp.yurastetsyc.notebook.activity

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        deleteDatabase(DataBaseHelper.DATABASE_NAME)

        mDataBase = DataBaseHelper(this).writableDatabase

        initViews()
    }

    private fun initViews() {
        val recyclerView = this.mRecyclerView
        recyclerView.adapter = NoteAdapter(mNotes)

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        this.fab.setOnClickListener {
            startActivityForResult(Intent(this, EditNoteActivity::class.java)
                    .putExtra(EditNoteActivity.NOTE_KEY, mDataBase?.let { it -> createNewNote(it) }),
                    EditNoteActivity.RQC_EDIT_NOTE)
        }
    }

    private fun createNewNote(database: SQLiteDatabase): Note {
        val cv = ContentValues()

        val colorWhite: String = "#FFFFFFFF"

        cv.put(DataBaseHelper.NOTES_CONTENT, "")
        cv.put(DataBaseHelper.NOTES_TIME, System.currentTimeMillis())
        cv.put(DataBaseHelper.NOTES_PRIORITY, Note.Priority.NORMAL.mPriority)
        cv.put(DataBaseHelper.NOTES_BACKGROUND, colorWhite)

        val id = database.insert(DataBaseHelper.TABLE_NOTES, null, cv)

        return Note(id, "", Calendar.getInstance(), Note.Priority.NORMAL, colorWhite)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EditNoteActivity.RQC_EDIT_NOTE) {
            val note: Note? = data?.extras?.get(EditNoteActivity.NOTE_KEY) as Note?
            note?.let {
                when (resultCode) {
                    EditNoteActivity.RSC_UPDATE_NOTE -> {
                        val id = it.mId
                        val index = mNotes.indexOfFirst { it.mId == id }
                        when (index) {
                            -1 -> {
                                mNotes.add(it)
                                mRecyclerView.adapter.notifyItemInserted(mNotes.size - 1)
                            }
                            else -> {
                                mNotes[index] = it
                                mRecyclerView.adapter.notifyItemChanged(index)
                            }
                        }
                    }

                    EditNoteActivity.RSC_DELETE_NOTE -> {
                        val id = it.mId
                        val index = mNotes.indexOfFirst { it.mId == id }
                        if (index > -1 && index < mNotes.size) {
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

    private fun deleteNoteFromDatabase(note: Note) {
        mDataBase?.delete(DataBaseHelper.TABLE_NOTES, "${DataBaseHelper.NOTES_ID} = ${note.mId}", null)
    }
}
