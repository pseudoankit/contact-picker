package lostankit7.android.contactlist.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import lostankit7.android.contactlist.R
import lostankit7.android.contactlist.model.Contact
import lostankit7.android.contactlist.base.AbstractViewHolder

open class ContactViewHolder(view: View) : AbstractViewHolder<Contact>(view) {

    private val image : ImageView = view.findViewById(R.id.ivContact)
    private val name : TextView = view.findViewById(R.id.tvName)
    private val phoneNumber : TextView = view.findViewById(R.id.tvPhoneNumber)

    override fun bind(element: Contact) {
        name.text = element.name
        phoneNumber.text = element.number
    }

    companion object {
        @LayoutRes val LAYOUT = R.layout.item_rv_contact
    }
}