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
    private val list: MutableList<Contact> = mutableListOf(),
    @LayoutRes private val layoutId: Int = R.layout.item_rv_contact,
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val holder = ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
        return holder
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

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.ivContact)
        private val name: TextView = view.findViewById(R.id.tvName)
        private val phoneNumber: TextView = view.findViewById(R.id.tvPhoneNumber)

        fun bind(element: Contact) {
            name.text = element.name
            phoneNumber.text = element.number
            element.image?.let { ContextCompat.getDrawable(image.context, it) }
        }
    }
}