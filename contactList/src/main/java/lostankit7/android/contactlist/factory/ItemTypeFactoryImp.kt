package lostankit7.android.contactlist.factory

import android.view.View
import lostankit7.android.contactlist.model.Contact
import lostankit7.android.contactlist.model.ErrorState
import lostankit7.android.contactlist.viewholder.ContactViewHolder
import lostankit7.android.contactlist.viewholder.ErrorStateViewHolder
import lostankit7.android.contactlist.base.AbstractViewHolder

class ItemTypeFactoryImp : ItemTypeFactory {
    override fun type(contact: Contact): Int {
        return ContactViewHolder.LAYOUT
    }

    override fun type(errorState: ErrorState): Int {
        return ErrorStateViewHolder.LAYOUT
    }

    override fun createViewHolder(parent: View, type: Int): AbstractViewHolder<*> {
        return when(type) {
            ErrorStateViewHolder.LAYOUT -> ErrorStateViewHolder(parent)
            ContactViewHolder.LAYOUT -> ContactViewHolder(parent)
            else -> createViewHolder(parent, type)
        }
    }
}