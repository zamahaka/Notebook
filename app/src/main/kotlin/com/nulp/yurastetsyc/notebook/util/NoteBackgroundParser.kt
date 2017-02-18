package com.nulp.yurastetsyc.notebook.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.view.WindowManager


/**
 * Created by Ura on 18.02.2017.
 */

/**
 * Provides Drawable for given background string
 * @from [String] to parse. Formats are:
 *                          C:#FFEEDDCC for color
 *                          R:DrawableRes for resource
 *                          U:Uri for File URI
 * @return corresponding [Drawable]
 */

object NoteBackgroundParser {

    val COLOR_FORMAT = "C:"
    val RESOURCE_FORMAT = "R:"
    val PATH_FORMAT = "U:"

    val DRAWABLE_ALPHA = 0x88

    fun getBackgroundDrawable(from: String, context: Context): Drawable {
        when (from.subSequence(0, 2)) {
            COLOR_FORMAT -> return parseColor(from.substring(2))
            RESOURCE_FORMAT -> return parseResource(from.substring(2), context)
            PATH_FORMAT -> return parsePath(from.substring(2), context)
            else -> return ColorDrawable(Color.TRANSPARENT)
        }
    }

    private fun parseColor(color: String): Drawable {
        return ColorDrawable(Color.parseColor(color))
    }

    private fun parseResource(color: String, context: Context): Drawable {
        val drawable = ContextCompat.getDrawable(context, context.resources.getIdentifier(
                "bg_note_$color", "drawable", context.packageName
        ))
        drawable.alpha = DRAWABLE_ALPHA
        return drawable
    }

    private fun parsePath(path: String, context: Context): Drawable {
        val uri = Uri.parse(path)
        val bitmap: Bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dSize = Point()
        windowManager.defaultDisplay.getSize(dSize)
        val scaledBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, dSize.x / 2, dSize.y / 2, true)
        val drawable = BitmapDrawable(context.resources, scaledBitmap)
        drawable.alpha = DRAWABLE_ALPHA
        return drawable
    }
}