package lostankit7.android.contactlist.entity

import androidx.annotation.DrawableRes
import lostankit7.android.contactlist.R

data class Contact(
    val name: String,
    val number: String,
    @DrawableRes val image: Int = R.drawable.ic_user_circle,
) {
    var isSelected = false
}


