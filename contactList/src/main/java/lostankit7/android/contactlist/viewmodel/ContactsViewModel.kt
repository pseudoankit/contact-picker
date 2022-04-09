package lostankit7.android.contactlist.viewmodel

import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lostankit7.android.contactlist.base.Result
import lostankit7.android.contactlist.entity.Contact
import lostankit7.android.contactlist.util.get

class ContactsViewModel : ViewModel() {

    private val _contactsLiveData: MutableLiveData<Result<List<Contact>>> = MutableLiveData()
    val contactsLiveData: LiveData<Result<List<Contact>>> = _contactsLiveData

    fun fetchContactList(contentResolver: ContentResolver) = viewModelScope.launch(Dispatchers.Main) {
        _contactsLiveData.value = Result.Loading()

        val list = mutableListOf<Contact>()

        val uri = ContactsContract.Contacts.CONTENT_URI
        val sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        val cursor = contentResolver.query(
            uri, null, null, null, sort
        )

        if (cursor == null || cursor.count == 0) {
            cursor?.close()
            _contactsLiveData.value = Result.Failure("")
            return@launch
        }

        while (cursor.moveToNext()) {
            val id = cursor get ContactsContract.Contacts._ID
            val name = cursor get ContactsContract.Contacts.DISPLAY_NAME
            val phoneCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                arrayOf(id), null
            )

            if (phoneCursor == null || !phoneCursor.moveToNext()) {
                continue
            }

            val number = phoneCursor get ContactsContract.CommonDataKinds.Phone.NUMBER
            if (name != null && number != null) {
                val contact = Contact(name, number)
                list.add(contact)
            }
            phoneCursor.close()
        }
        cursor.close()
        _contactsLiveData.value = Result.Success(list)
    }

}