package lostankit7.android.contactlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lostankit7.android.contactlist.factory.ItemTypeFactory
import lostankit7.android.contactlist.base.AbstractViewHolder
import lostankit7.android.contactlist.base.BaseItemModel

class ContactsAdapter(
    private val adapterTypeFactory: ItemTypeFactory,
    private val list: MutableList<BaseItemModel> = mutableListOf(),
) : RecyclerView.Adapter<AbstractViewHolder<BaseItemModel>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): AbstractViewHolder<BaseItemModel> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return adapterTypeFactory.createViewHolder(view, viewType)
                as AbstractViewHolder<BaseItemModel>
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type(adapterTypeFactory)
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<BaseItemModel>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun addItems(items: List<BaseItemModel>) {
        this.list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}