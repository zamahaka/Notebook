package com.nulp.yurastetsyc.notebook.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.nulp.yurastetsyc.notebook.R
import com.nulp.yurastetsyc.notebook.data.Note
import kotlinx.android.synthetic.main.activity_edit_note.*
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.nulp.yurastetsyc.notebook.util.NoteBackgroundParser


class EditNoteActivity : AppCompatActivity() {

    private val RQC_LOAD_IMAGE: Int = 1

    companion object {
        val NOTE_KEY: String = "note_key"

        val RQC_EDIT_NOTE: Int = 1

        val RSC_UPDATE_NOTE: Int = 1
        val RSC_DELETE_NOTE: Int = 2
    }

    var mNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        mNote = intent?.extras?.get(NOTE_KEY) as Note?

        supportActionBar?.title = getActionBarTitle(mNote)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mEditText.setText(mNote?.mContent)
        mEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                mNote?.mContent = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mFab.setOnClickListener { startImagePicker() }
    }

    private fun getActionBarTitle(note: Note?): String {
        val a: String? = note?.let {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
            return simpleDateFormat.format(it.mCreationDate.time)
        }
        return a ?: getString(R.string.error_edit_note_null)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        setProperResult()
        super.onBackPressed()
    }

    private fun setProperResult() {
        val intent: Intent = Intent()
                .putExtra(NOTE_KEY, mNote)
        val result: Int
        when (mNote?.mContent?.length) {
            0 -> result = RSC_DELETE_NOTE
            else -> result = RSC_UPDATE_NOTE
        }
        setResult(result, intent)
    }

    fun startImagePicker() {
        val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, RQC_LOAD_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQC_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            val uri: Uri = data.data
            mNote?.mBackGround = "${NoteBackgroundParser.PATH_FORMAT}$uri"
            mRoot.background = BitmapDrawable(resources, BitmapFactory.decodeStream(contentResolver.openInputStream(uri)))
        }
    }
}
