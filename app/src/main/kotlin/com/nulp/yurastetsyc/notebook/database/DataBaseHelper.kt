package com.nulp.yurastetsyc.notebook.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by Yura Stetsyc on 2/15/17.
 */
class DataBaseHelper(mContext: Context) :
        SQLiteOpenHelper(mContext, DataBaseHelper.DATABASE_NAME, null, DataBaseHelper.DATABASE_VERSION) {

    val TAG: String = DataBaseHelper::class.java.simpleName

    companion object {
        val DATABASE_NAME = "NOTE_DATABASE.db"
        val DATABASE_VERSION = 1

        val TABLE_NOTES: String = "NOTES"
        val NOTES_ID: String = "ID"
        val NOTES_TIME: String = "TIME"
        val NOTES_CONTENT: String = "CONTENT"
        val NOTES_PRIORITY: String = "PRIORITY"
        val NOTES_BACKGROUND: String = "BACKGROUND"
    }

    val DATABASE_CREATE_NOTES =
            "CREATE TABLE if not exists $TABLE_NOTES" + " (" +
                    "$NOTES_ID integer PRIMARY KEY autoincrement," +
                    "$NOTES_CONTENT text," +
                    "$NOTES_TIME long," +
                    "$NOTES_PRIORITY text," +
                    "$NOTES_BACKGROUND text" +
                    ")"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "Creating data base")
        db?.execSQL(DATABASE_CREATE_NOTES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}