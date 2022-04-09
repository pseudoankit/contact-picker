package lostankit7.android.contactlist.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import lostankit7.android.contactlist.R
import lostankit7.android.contactlist.adapter.ContactsAdapter
import lostankit7.android.contactlist.base.Result
import lostankit7.android.contactlist.entity.Contact
import lostankit7.android.contactlist.util.PermissionUtils
import lostankit7.android.contactlist.util.hide
import lostankit7.android.contactlist.util.isVisible
import lostankit7.android.contactlist.util.show
import lostankit7.android.contactlist.viewmodel.ContactsViewModel

class ContactsListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    /**
     * listener which is invoked each time any contact is selected or unselected
     * first argument is current selected or unselected item
     * second argument is total number of selected contacts
     */
    var contactSelectedListener: ((Contact, Int) -> Unit)? = null

    /** returns list of selected contacts */
    val selectedContactsList get() = run { adapter.selectedContacts }

    private val viewModel by lazy { ViewModelProvider(context as AppCompatActivity)[ContactsViewModel::class.java] }
    private lateinit var observer: Observer<Result<List<Contact>>>

    private val adapter = ContactsAdapter(::contactSelectedListener)

    private val headerView by lazy(LazyThreadSafetyMode.NONE) { findViewById<ConstraintLayout>(R.id.header) }

    init {
        inflate(context, R.layout.contact_list_view, this)
        setUpView()
        observeLiveData()
        loadContacts()
    }

    private fun setUpView() {
        findViewById<RecyclerView>(R.id.rv_contacts).adapter = adapter
        updateContactSelectedText(0)

        setUpListener()
    }

    private fun setUpListener() {
        findViewById<ImageView>(R.id.ivSelectAll).setOnClickListener {
            adapter.selectAll()
            updateSelectAllImage(true)
            updateContactSelectedText(adapter.itemCount)
        }

        findViewById<ImageView>(R.id.ivDismissSelection).setOnClickListener {
            adapter.unSelectAll()
            headerView.hide()
            updateContactSelectedText(0)
        }
    }

    private fun observeLiveData() {
        observer = Observer { response ->
            hideProgressBar()
            when (response) {
                is Result.Success -> {
                    if (response.data.isNullOrEmpty()) {
                        showErrorText(context.getString(R.string.error_no_contacts_found))
                    } else {
                        adapter.addItems(response.data)
                    }
                }
                is Result.Failure -> {
                    showErrorText(resources.getString(R.string.error_failed_to_load_contact))
                }
                is Result.Loading -> {
                    showProgressBar()
                }
            }
        }
        viewModel.contactsLiveData.observeForever(observer)
    }

    private fun contactSelectedListener(contact: Contact) {
        contactSelectedListener?.invoke(contact, adapter.selectedContactsCount)

        if (adapter.selectedContactsCount > 0 && !headerView.isVisible())
            headerView.show()
        updateSelectAllImage(adapter.selectedContactsCount == adapter.itemCount)
        updateContactSelectedText(adapter.selectedContactsCount)
    }

    private fun updateSelectAllImage(allItemsSelected: Boolean) {
        findViewById<ImageView>(R.id.ivSelectAll).setImageDrawable(
            ContextCompat.getDrawable(
                context, if (allItemsSelected) R.drawable.ic_selected else R.drawable.ic_circle
            )
        )
    }

    private fun updateContactSelectedText(count: Int) {
        findViewById<TextView>(R.id.tvSelectedCount).text = String.format(
            resources.getString(R.string.text_selected_contacts), count, adapter.itemCount
        )
    }

    private fun showProgressBar() {
        findViewById<ProgressBar>(R.id.progressBar).show()
    }

    private fun hideProgressBar() {
        findViewById<ProgressBar>(R.id.progressBar).hide()
    }

    private fun showErrorText(error: String) {
        findViewById<TextView>(R.id.tvError).apply {
            show()
            text = error
        }
    }

    fun progressBarColor(@ColorRes color: Int) {
        findViewById<ProgressBar>(R.id.progressBar).progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, color))
    }

    /** loads contacts from data base */
    fun loadContacts() {
        if (PermissionUtils.hasReadContactPermission(context)) {
            showErrorText(context.getString(R.string.error_permission_not_found))
            return
        }
        viewModel.fetchContactList(context.contentResolver)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (::observer.isInitialized)
            viewModel.contactsLiveData.removeObserver(observer)
    }
}