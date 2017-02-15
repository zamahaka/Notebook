package com.nulp.yurastetsyc.notebook.activity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import com.nulp.yurastetsyc.notebook.R
import com.nulp.yurastetsyc.notebook.adapter.NoteAdapter
import com.nulp.yurastetsyc.notebook.data.Note
import com.nulp.yurastetsyc.notebook.database.DataBaseHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val mNotes: MutableList<Note> = ArrayList()
    val mDataBase: SQLiteDatabase = DataBaseHelper(this).writableDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNotes.addAll(Arrays.asList(
                Note(0, "Note 0", Calendar.getInstance()),
                Note(1, "Note 1", Calendar.getInstance()),
                Note(2, "Note 2", Calendar.getInstance()),
                Note(3, "Note 3", Calendar.getInstance()),
                Note(4, "Note 4", Calendar.getInstance()),
                Note(5, "Note 5", Calendar.getInstance()),
                Note(6, "Note 6", Calendar.getInstance()),
                Note(7, "Note 7", Calendar.getInstance())
        ))

        initViews()
    }

    private fun initViews() {
        val recyclerView = this.recyclerView
        recyclerView.adapter = NoteAdapter(mNotes)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        this.fab.setOnClickListener { startActivity(Intent(this, EditNoteActivity::class.java)) }
    }

    private fun createNewNote(database: SQLiteDatabase): Note {
        val cursor = database.rawQuery("SELECT ${DataBaseHelper.ID} from ${DataBaseHelper.TABLE_NOTES}" +
                " order by ${DataBaseHelper.ID} DESC limit 1", null)
        val id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.ID))
        cursor.close()
        return Note(id, "", Calendar.getInstance())
    }
}
