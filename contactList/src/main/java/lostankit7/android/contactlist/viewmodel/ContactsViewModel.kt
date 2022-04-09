package lostankit7.android.contactlist.viewmodel

import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lostankit7.android.contactlist.base.Result
import lostankit7.android.contactlist.entity.Contact
import lostankit7.android.contactlist.util.get
import lostankit7.android.contactlist.util.launchMain

class ContactsViewModel : ViewModel() {

    private val _contactsLiveData: MutableLiveData<Result<List<Contact>>> = MutableLiveData()
    val contactsLiveData: LiveData<Result<List<Contact>>> = _contactsLiveData

    fun fetchContactList(cResolver: ContentResolver) = viewModelScope.launch(Dispatchers.IO) {
        launchMain {
            _contactsLiveData.value = Result.Loading()
        }

        val list = mutableListOf<Contact>()

        val uri = ContactsContract.Contacts.CONTENT_URI
        val sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        val cursor = cResolver.query(uri, null, null, null, sort)

        if (cursor == null || cursor.count == 0) {
            cursor?.close()
            launchMain {
                _contactsLiveData.value =
                    if (cursor == null) Result.Failure() else Result.Success(emptyList())
            }
            return@launch
        }

        while (cursor.moveToNext()) {
            val id = cursor get ContactsContract.Contacts._ID
            val name = cursor get ContactsContract.Contacts.DISPLAY_NAME
            val phoneCursor = cResolver.query(
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
        launchMain {
            _contactsLiveData.value = Result.Success(list)
        }
    }

}