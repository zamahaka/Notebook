package com.nulp.yurastetsyc.notebook.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Yura Stetsyc on 2/14/17.
 */
class Note(val mId: Int, val mContent: String, val mCreationDate: Calendar) : Parcelable {

    val CREATOR = Parcelable.Creator<Note>()

    constructor(from: Parcel) : this(from.readInt(), from.readString(), from.readSerializable() as Calendar)

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(mId)
        dest?.writeString(mContent)
        dest?.writeSerializable(mCreationDate)
    }

    override fun describeContents(): Int {
        return 0
    }

}