package pe.com.redcups.juergapp_android.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_event_add.*
import pe.com.redcups.core.VolleyMultipartRequest
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.utilities.DateUtil
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.events.EventAddViewModel

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.databinding.FragmentEventAddBinding
import java.io.IOException
import java.util.*

class EventAddFragment : Fragment(), DatePickerDialog.OnDateSetListener, OnMapReadyCallback {
    private val IMG_RESULT = 1
    var ImageDecode: String? = null
    val I_HAVE_PERIMISSIONS = 1
    var intent: Intent? = null


    private val viewModel: EventAddViewModel by viewModels {
        InjectorUtils.provideEventAddViewModelFactory(requireContext())
    }

    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentEventAddBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_event_add, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewmodel =viewModel

        return binding.root
    }

    fun createDatePickerInstance(calendar: Calendar) {
        calendar.time = viewModel.event.value!!.date
        datePickerDialog = DatePickerDialog.newInstance(
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.minDate = Calendar.getInstance(TimeZone.getDefault())
        datePickerDialog.show(fragmentManager!!,"DatePickerDialog")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val calendar = Calendar.getInstance(TimeZone.getDefault())

        date_text_input_layout.setEndIconOnClickListener {
            createDatePickerInstance(calendar)
        }
        date_edit_text.setOnClickListener {
            createDatePickerInstance(calendar)
        }

        viewModel.event.observe(this, Observer {
            // for date string
            date_edit_text.setText(DateUtil.toCustomString(calendar))
            val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
            mapFragment.getMapAsync(this)
        })

        add_event_button.setOnClickListener {
            viewModel.persistEvent()
            findNavController().navigateUp()
        }
        event_add_image.setOnClickListener {

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(context!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this.activity!!,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        I_HAVE_PERIMISSIONS)
                }
            } else {
                // Permission has already been granted
                intent = Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, IMG_RESULT)
            }

        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        date_edit_text.setText("$dayOfMonth-${(monthOfYear+1).toString().padStart(2,'0')}-$year")
        viewModel.event.value!!.date =  GregorianCalendar(year,monthOfYear,dayOfMonth).time
        date_edit_text.requestFocus()
    }

    override fun onMapReady(gMap: GoogleMap) {
        gMap.setOnMapClickListener {
            viewModel.event.value!!.latitude = it.latitude
            viewModel.event.value!!.longitude = it.longitude
            val eventPos = LatLng(viewModel.event.value!!.latitude,viewModel.event.value!!.longitude)
            gMap.clear()
            gMap.moveCamera(CameraUpdateFactory.newLatLng(eventPos))
            val cameraPosition = CameraPosition.Builder()
                .target(eventPos)
                .zoom(17.0f)
                .build()
            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            gMap.addMarker(MarkerOptions().position(eventPos).title(viewModel.event.value!!.name))

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try
        {
            if ((requestCode === IMG_RESULT && resultCode === RESULT_OK
                        && null != data))
            {
                val URI = data.getData()
                val FILE = arrayOf<String>(MediaStore.Images.Media.DATA)
                val cursor = context!!.contentResolver.query(URI,
                    FILE, null, null, null)
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(FILE[0])
                ImageDecode = cursor.getString(columnIndex)
                cursor.close()
                event_add_image.scaleType = ImageView.ScaleType.CENTER_CROP
                //viewModel.event.value!!.picture =  BitmapFactory.decodeFile(ImageDecode)
                event_add_image.setImageBitmap(
                    BitmapFactory
                        .decodeFile(ImageDecode))


                // send data to API

                val url = "https://juergapp.wemake.pe/upload_event/"
                val multipartRequest = object :
                    VolleyMultipartRequest(Request.Method.POST, url, object : Response.Listener<NetworkResponse> {
                        override fun onResponse(response: NetworkResponse) {
                            val resultResponse = String(response.data)
                            // parse success output
                        }
                    }, object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError) {
                            error.printStackTrace()
                        }
                    }) {
                    override fun getParams():HashMap<String, String> {
                        return hashMapOf("id" to "3")
                    }
                    // file name could found file base or direct access from real path
                    // for now just get bitmap data from ImageView

                    override fun getByteData(): Map<String, DataPart>{
                        val params = HashMap<String, DataPart>()
                        event_add_image.let {
                            params.put(
                                "file", DataPart(
                                    "event_image.jpg",
                                    readBytes(this@EventAddFragment.context!!, URI),
                                    "image/jpeg"
                                )
                            )

                        }
                        return params
                    }
                }
                //AppController.getInstance().addRequest(multipartRequest);

            }
        }
        catch (e:Exception) {
            Log.d("yolo", e.toString())
            Toast.makeText(context!!, "Please try again", Toast.LENGTH_LONG)
                .show()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            I_HAVE_PERIMISSIONS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    intent = Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, IMG_RESULT)
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
    @Throws(IOException::class)
    private fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }

}
