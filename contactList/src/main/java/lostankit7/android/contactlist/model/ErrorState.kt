package lostankit7.android.contactlist.model

import lostankit7.android.contactlist.base.BaseItemModel
import lostankit7.android.contactlist.factory.ItemTypeFactory

data class ErrorState(
    val title: String,
    val description: String,
    val errorImage: Int,
) : BaseItemModel {
    override fun type(typeFactory: ItemTypeFactory): Int {
        return typeFactory.type(this)
    }
}