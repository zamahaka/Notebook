package com.nulp.yurastetsyc.notebook.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by Yura Stetsyc on 2/15/17.
 */
class DataBaseHelper(mContext: Context) :
        SQLiteOpenHelper(mContext, "NOTE_DATABASE.db", null, 4) {

    val TAG: String = DataBaseHelper::class.java.simpleName

    companion object {
        val TABLE_NOTES = "NOTES"
        public val ID: String = "ID"
        public val TIME: String = "TIME"
        public val TEXT: String = "TEXT"
        public val PRIORITY: String = "PRIORITY"
        public val BACKGROUND: String = "BACKGROUND"
    }

    val DATABASE_CREATE_NOTES =
            "CREATE TABLE_NOTES if not exists " + TABLE_NOTES + " (" +
                    "$ID integer PRIMARY KEY autoincrement," +
                    "$TIME long," +
                    "$TEXT text" +
                    "$PRIORITY int" +
                    "$BACKGROUND int" +
                    ")"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "Creating data base")
        db?.execSQL(DATABASE_CREATE_NOTES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}