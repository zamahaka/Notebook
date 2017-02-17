package com.nulp.yurastetsyc.notebook.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Yura Stetsyc on 2/14/17.
 */
class Note(val mId: Long,
           val mCreationDate: Calendar,
           var mContent: String,
           var mPriority: Priority,
           var mBackGround: String) : Parcelable {
    enum class Priority {
        LOW, NORMAL, HIGH;
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Note> = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel): Note = Note(source)
            override fun newArray(size: Int): Array<Note?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readSerializable() as Calendar,
            source.readString(),
            Priority.values()[source.readInt()],
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(mId)
        dest?.writeSerializable(mCreationDate)
        dest?.writeString(mContent)
        dest?.writeInt(mPriority.ordinal)
        dest?.writeString(mBackGround)
    }

    override fun toString(): String {
        return "Note(mId=$mId, mContent='$mContent', mCreationDate=$mCreationDate," +
                " mPriority=$mPriority, mBackGround='$mBackGround')"
    }

}