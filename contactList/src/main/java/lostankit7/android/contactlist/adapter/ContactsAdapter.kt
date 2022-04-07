package lostankit7.android.contactlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lostankit7.android.contactlist.R
import lostankit7.android.contactlist.model.Contact

class ContactsAdapter(private val list: MutableList<Contact> = mutableListOf()) :
    RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_contact, parent, false)
        return ContactViewHolder(view)
    }

    fun addItems(items: List<Contact>) {
        this.list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}