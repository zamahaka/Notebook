package com.nulp.yurastetsyc.notebook.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nulp.yurastetsyc.notebook.R
import com.nulp.yurastetsyc.notebook.data.Note

class EditNoteActivity : AppCompatActivity() {

    companion object {
        val NOTE_KEY: String = "note_key"

        val RQC_EDIT_NOTE: Int = 1

        val RSC_ADD_NOTE: Int = 1
        val RSC_UPDATE_NOTE: Int = 2
        val RSC_DELETE_NOTE: Int = 3
    }

    var mNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        mNote = intent?.extras?.get(NOTE_KEY) as Note?
        Log.d("myLog", mNote.toString())
    }

    override fun onBackPressed() {
        setResult(RSC_ADD_NOTE, Intent()
                .putExtra(NOTE_KEY, mNote))
        super.onBackPressed()
    }
}
