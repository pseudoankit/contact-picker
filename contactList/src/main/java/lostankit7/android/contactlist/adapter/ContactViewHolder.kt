package lostankit7.android.contactlist.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lostankit7.android.contactlist.R
import lostankit7.android.contactlist.model.Contact

open class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val image : ImageView = view.findViewById(R.id.ivContact)
    private val name : TextView = view.findViewById(R.id.tvName)
    private val phoneNumber : TextView = view.findViewById(R.id.tvPhoneNumber)

    fun bind(contact: Contact) {
        name.text = contact.name
        phoneNumber.text = contact.number
    }

}