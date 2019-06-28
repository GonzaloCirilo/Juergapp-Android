package pe.com.redcups.juergapp_android.fragment


import android.Manifest
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pe.com.redcups.juergapp_android.R
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import kotlinx.android.synthetic.main.fragment_contacts.*
import androidx.recyclerview.widget.LinearLayoutManager
import pe.com.redcups.juergapp_android.adapter.Contact
import pe.com.redcups.juergapp_android.adapter.ContactsAdapter


// Sets the columns to retrieve for the user profile
val projection = arrayOf(
    ContactsContract.Profile._ID,
    ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
    ContactsContract.Profile.LOOKUP_KEY,
    ContactsContract.Profile.PHOTO_THUMBNAIL_URI,
    ContactsContract.Profile.HAS_PHONE_NUMBER,
    ContactsContract.CommonDataKinds.Phone.NUMBER
)

// custom defined read contacts
const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10

/**
 * A simple [Fragment] subclass.
 *
 */
class Contacts : Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {

    lateinit var adapter:ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    fun askForPermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity!!.applicationContext,
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            loaderManager.initLoader(0, null, this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                    // If request is cancelled, the result arrays are empty.
                    if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.


                        loaderManager.initLoader(0, null, this)
                    } else {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                    return
                }

                // Add other 'when' lines to check for other
                // permissions this app might request.
                else -> {
                    // Ignore all other requests.
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        askForPermissions()

        adapter = ContactsAdapter(view.context)
        // Sets the adapter for the ListView
        recycler_view_contact.adapter = adapter
        recycler_view_contact.layoutManager = LinearLayoutManager(view.context)


    }
    override fun onLoaderReset(loader: Loader<Cursor>) {
        // Delete the reference to the existing Cursor
    }
    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        var contacts: MutableList<Contact> = mutableListOf()

        if (cursor.count > 0) {
            cursor.moveToFirst()

            do {
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contacts.add(Contact(name, phone))
            } while (cursor.moveToNext())
        }
        adapter.setContacts(contacts)
    }
    override fun onCreateLoader(loaderId: Int, args: Bundle?): Loader<Cursor> {
        val contentUri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        // Starts the query
        return CursorLoader(
            activity!!.applicationContext,
                contentUri,
                projection,
                null,
                null,
                null
            )
    }

}
