package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_event_detail.*
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.events.EventDetailViewModel

import pe.com.redcups.juergapp_android.R

class EventDetailFragment : Fragment(), OnMapReadyCallback {

    private var lat: Double = -34.0
    private var lon: Double = 151.0


    override fun onMapReady(gMap: GoogleMap) {
        val eventPos = LatLng(lat,lon)
        gMap.isBuildingsEnabled = true
        gMap.uiSettings.isZoomControlsEnabled = false
        gMap.uiSettings.isZoomGesturesEnabled = false
        gMap.uiSettings.isScrollGesturesEnabled = false
        gMap.uiSettings.isRotateGesturesEnabled = false
        gMap.addMarker(MarkerOptions().position(eventPos).title(event_name_label.text.toString()))
        gMap.moveCamera(CameraUpdateFactory.newLatLng(eventPos))
        val cameraPosition = CameraPosition.Builder()
            .target(eventPos)
            .zoom(17.0f)
            .build()
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private val safeArgs: EventDetailFragmentArgs by navArgs()

    private val viewModel: EventDetailViewModel by viewModels{
        InjectorUtils.provideEventDetailViewModelFactory(requireActivity(), safeArgs.eventId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = true

        viewModel.event.observe(this, Observer {
            it?.also { e ->
                event_name_label.text = e.name
                event_address.text = e.address
                lat = e.latitude
                lon = e.longitude
                event_host.text = e.id.toString()
                val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        })
    }
}
