package com.nulp.yurastetsyc.notebook.data

import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Yura Stetsyc on 2/14/17.
 */
class Note(val mId: Long, val mContent: String, val mCreationDate: Calendar,
           val mPriority: Priority, val mBackGround: String) : Parcelable {
    enum class Priority(val mPriority: Int) {
        LOW(-1), NORMAL(0), HIGH(1);
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Note> = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel): Note = Note(source)
            override fun newArray(size: Int): Array<Note?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readLong(), source.readString(),
            source.readSerializable() as Calendar,
            Priority.values()[source.readInt()], source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(mId)
        dest?.writeString(mContent)
        dest?.writeSerializable(mCreationDate)
        dest?.writeInt(mPriority.ordinal)
        dest?.writeString(mBackGround)
    }

    override fun toString(): String {
        return "Note(mId=$mId, mContent='$mContent', mCreationDate=$mCreationDate," +
                " mPriority=$mPriority, mBackGround='$mBackGround')"
    }


}