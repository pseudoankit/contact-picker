package lostankit7.android.contactlist.entity

import androidx.annotation.DrawableRes

data class Contact(
    val name: String,
    val number: String,
    @DrawableRes val image: Int? = null,
) {
    var isSelected = false
}


