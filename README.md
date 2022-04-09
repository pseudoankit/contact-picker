# Getting Started
This repository contains a custom view, in which device contact list is displayed

# Implementation
Go to Settings.gradle, inside repositories block -> <br/>
```
repositories {  
    maven { url 'https://jitpack.io' }
}
```

Go to Settings.gradle, inside repositories block -> <br/>
```
implementation 'com.github.lostankit7:multipleContactPicker:v1.1'
```

In your .xml file 

```
<lostankit7.android.contactlist.view.ContactsListView
        android:id="@+id/contactsView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

Exposed methods

```
  contactsView.loadContacts() //refresh the view or retrieve and load contacts again
  
  contactsView.contactSelectedListener = { contact, contactsSelectedCount -> 
    //do your stuff here
  }
  
  contactsView.selectedContactsList //returns list of selected contacts 
```
