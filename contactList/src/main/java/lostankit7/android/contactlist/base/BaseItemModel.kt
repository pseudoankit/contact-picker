package lostankit7.android.contactlist.base

import lostankit7.android.contactlist.factory.ItemTypeFactory

interface BaseItemModel {
    fun type(typeFactory: ItemTypeFactory) : Int
}