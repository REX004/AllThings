package com.tttrfge.deliveryapp.sesion3.fragments.profilepac

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tttrfge.deliveryapp.R

class TrackingPackage : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val defaultLocation = LatLng(37.7749, -122.4194)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tracking_package, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.addMarker(MarkerOptions().position(defaultLocation).title("Default Location"))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))


    }
}
