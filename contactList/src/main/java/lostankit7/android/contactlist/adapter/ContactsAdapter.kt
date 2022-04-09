package lostankit7.android.contactlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import lostankit7.android.contactlist.R
import lostankit7.android.contactlist.entity.Contact

class ContactsAdapter(
    private val contactSelectedListener: (Contact, Int) -> Unit,
    private val list: MutableList<Contact> = mutableListOf(),
    @LayoutRes private val layoutId: Int = R.layout.item_rv_contact,
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private val _selectedContacts = mutableSetOf<Contact>()
    val selectedContacts = _selectedContacts.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun addItems(items: List<Contact>) {
        this.list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(
        private val view: View,
    ) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.ivContact)
        private val name: TextView = view.findViewById(R.id.tvName)
        private val phoneNumber: TextView = view.findViewById(R.id.tvPhoneNumber)

        fun bind(contact: Contact) {
            name.text = contact.name
            phoneNumber.text = contact.number
            contact.image?.let { ContextCompat.getDrawable(image.context, it) }

            image.setOnClickListener { itemClicked(contact) }
            view.setOnLongClickListener {
                itemClicked(contact)
                true
            }
        }

        private fun itemClicked(contact: Contact) {
            contact.isSelected = !contact.isSelected
            if (contact.isSelected) {
                image.setImageDrawable(
                    ContextCompat.getDrawable(view.context, R.drawable.ic_selected)
                )
                _selectedContacts.add(contact)
            } else {
                image.setImageDrawable(
                    ContextCompat.getDrawable(view.context, R.drawable.ic_user_circle)
                )
                _selectedContacts.remove(contact)
            }
            contactSelectedListener.invoke(contact, _selectedContacts.size)

            if (_selectedContacts.isEmpty()) {

            } else {

            }
        }
    }
}