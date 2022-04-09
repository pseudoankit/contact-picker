package lostankit7.android.contactlist.util

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE

@SuppressLint("Range")
infix fun Cursor.get(columnName: String): String? {
    return getString(getColumnIndex(columnName))
}