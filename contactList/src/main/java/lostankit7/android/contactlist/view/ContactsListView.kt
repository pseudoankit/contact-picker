package lostankit7.android.contactlist.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import lostankit7.android.contactlist.R
import lostankit7.android.contactlist.adapter.ContactsAdapter
import lostankit7.android.contactlist.base.Result
import lostankit7.android.contactlist.entity.Contact
import lostankit7.android.contactlist.util.PermissionUtils
import lostankit7.android.contactlist.viewmodel.ContactsViewModel

class ContactsListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val viewModel by lazy { ViewModelProvider(context as AppCompatActivity)[ContactsViewModel::class.java] }
    private lateinit var observer: Observer<Result<List<Contact>>>

    private val adapter = ContactsAdapter()

    init {
        inflate(context, R.layout.contact_list_view, this)
        //todo add progress bar
        setUpView()
        observeLiveData()
    }

    private fun observeLiveData() {
        observer = Observer { response ->
            when (response) {
                is Result.Success -> {
                    if (response.data.isNullOrEmpty()) {
                        //todo show empty list
                    } else {
                        adapter.addItems(response.data)
                    }
                }
                is Result.Failure -> {
                    //todo show failure view
                }
                is Result.Loading -> {
                    //todo show loading state
                }
            }
        }
        viewModel.contactsLiveData.observeForever(observer)
    }

    private fun setUpView() {
        findViewById<RecyclerView>(R.id.rv_contacts).adapter = adapter
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (::observer.isInitialized)
            viewModel.contactsLiveData.removeObserver(observer)
    }

    fun loadContacts() {
        if (PermissionUtils.hasReadContactPermission(context)) {
            //todo display no permission found
            return
        }
        viewModel.fetchContactList(context.contentResolver)
    }
}