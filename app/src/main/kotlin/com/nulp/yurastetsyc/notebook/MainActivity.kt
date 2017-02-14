package com.nulp.yurastetsyc.notebook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nulp.yurastetsyc.notebook.adapter.NoteAdapter
import com.nulp.yurastetsyc.notebook.data.Note
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val mNotes: MutableList<Note> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNotes.addAll(Arrays.asList(
                Note("Note 0", Calendar.getInstance()),
                Note("Note 1", Calendar.getInstance()),
                Note("Note 2", Calendar.getInstance()),
                Note("Note 3", Calendar.getInstance()),
                Note("Note 4", Calendar.getInstance()),
                Note("Note 5", Calendar.getInstance()),
                Note("Note 6", Calendar.getInstance()),
                Note("Note 7", Calendar.getInstance())
        ))

        initViews()
    }

    private fun initViews() {
        this.recyclerView.adapter = NoteAdapter(mNotes)
    }
}
