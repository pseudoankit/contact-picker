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
    private val contactSelectedListener: (Contact) -> Unit,
    private val list: MutableList<Contact> = mutableListOf(),
    @LayoutRes private val layoutId: Int = R.layout.item_rv_contact,
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    val selectedContacts get() = list.filter { it.isSelected }

    var selectedContactsCount = 0
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun unSelectAll() {
        if (selectedContactsCount == 0) return
        selectedContactsCount = 0
        list.forEach {
            it.isSelected = false
        }
        notifyItemRangeChanged(0, list.size)
    }

    fun selectAll() {
        if (selectedContactsCount == list.size) return
        selectedContactsCount = list.size
        list.forEach {
            it.isSelected = true
        }
        notifyItemRangeChanged(0, list.size)
    }

    fun addItems(items: List<Contact>) {
        this.list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(
        private val view: View,
    ) : RecyclerView.ViewHolder(view) {

        private val ivProfile: ImageView = view.findViewById(R.id.ivContact)
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvPhoneNumber: TextView = view.findViewById(R.id.tvPhoneNumber)

        fun bind(contact: Contact) = with(contact) {
            tvName.text = name
            tvPhoneNumber.text = number
            ivProfile.setImageDrawable(
                ContextCompat.getDrawable(
                    ivProfile.context,
                    if (isSelected) R.drawable.ic_selected else image
                )
            )

            ivProfile.setOnClickListener { itemClicked(contact) }
            view.setOnLongClickListener {
                itemClicked(contact)
                true
            }
            //todo enable and disable long click listener
        }

        private fun itemClicked(contact: Contact) {
            contact.isSelected = !contact.isSelected
            if (contact.isSelected) {
                ivProfile.setImageDrawable(
                    ContextCompat.getDrawable(view.context, R.drawable.ic_selected)
                )
                selectedContactsCount++
            } else {
                ivProfile.setImageDrawable(
                    ContextCompat.getDrawable(view.context, R.drawable.ic_user_circle)
                )
                selectedContactsCount--
            }
            contactSelectedListener.invoke(contact)
        }
    }
}