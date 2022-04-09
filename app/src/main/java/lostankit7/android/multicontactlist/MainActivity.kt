package lostankit7.android.multicontactlist

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lostankit7.android.contactlist.adapter.ContactsAdapter
import lostankit7.android.contactlist.view.ContactsListView

class MainActivity : AppCompatActivity() {

    private val contactsView : ContactsListView by lazy {
        findViewById(R.id.contactsView)
    }

    companion object {
        private const val REQUEST_CONTACT = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //checkPermission()
    }

    private fun loadContacts() {
        contactsView.loadContacts()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.READ_CONTACTS), REQUEST_CONTACT
            )
        } else {
            loadContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CONTACT && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            loadContacts()
        }
    }
}