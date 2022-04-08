package lostankit7.android.contactlist.factory

import android.view.View
import lostankit7.android.contactlist.model.Contact
import lostankit7.android.contactlist.model.ErrorState
import lostankit7.android.contactlist.base.AbstractViewHolder

interface ItemTypeFactory {
    fun type(contact: Contact): Int
    fun type(errorState: ErrorState): Int
    fun createViewHolder(parent: View, type: Int): AbstractViewHolder<*>
}