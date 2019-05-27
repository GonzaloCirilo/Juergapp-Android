package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_event_add.*
import kotlinx.android.synthetic.main.fragment_event_detail.*
import pe.com.redcups.core.utilities.DateUtil
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.events.EventAddViewModel

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.databinding.FragmentEventAddBinding
import java.util.*

class EventAddFragment : Fragment(), DatePickerDialog.OnDateSetListener, OnMapReadyCallback {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance(TimeZone.getDefault())

        date_button.setOnClickListener {
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

        viewModel.event.observe(this, Observer {
            // for date string
            date_picker_text_view.text = DateUtil.toCustomString(calendar)
            val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
            mapFragment.getMapAsync(this)
        })

        add_event_button.setOnClickListener {
            viewModel.persistEvent()
            findNavController().navigateUp()
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        date_picker_text_view.text = "$dayOfMonth-${(monthOfYear+1).toString().padStart(2,'0')}-$year"
        viewModel.event.value!!.date =  GregorianCalendar(year,monthOfYear,dayOfMonth).time
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
}
