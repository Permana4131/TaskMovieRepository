package com.del.testandroiddeveloper.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.preference.PreferenceManager
import com.del.testandroiddeveloper.R
import com.del.testandroiddeveloper.databinding.FragmentLocationBinding

import org.osmdroid.util.GeoPoint

import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider

import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay





class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var map : MapView
    private var myLatitude: Double = 0.0
    private var myLongitude: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        activity?.actionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInstance().load(binding.root.context, PreferenceManager.getDefaultSharedPreferences(binding.root.context))

        map = binding.map
        if (myLatitude == 0.0 || myLongitude == 0.0)
            getLastKnownLocation(binding.root.context)

    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    fun getLastKnownLocation(context: Context) {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> = locationManager.getProviders(true)
        var location: Location? = null
        for (i in providers.size - 1 downTo 0) {
            location= locationManager.getLastKnownLocation(providers[i])
            if (location != null)
                break
        }
        if (location != null) {
            myLatitude = location.latitude
            myLongitude = location.longitude
            binding.textLatitude.text = "${getString(R.string.latitude)} : ${location.latitude}"
            binding.textLongitude.text = "${getString(R.string.longitude)} : ${location.longitude}"

            map.setTileSource(TileSourceFactory.MAPNIK)
            val mapController = map.controller
            map.setBuiltInZoomControls(true)
            map.setMultiTouchControls(true)

            val startPoint = GeoPoint(myLatitude, myLongitude)
            mapController.animateTo(startPoint)
            val mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
            mLocationOverlay.enableMyLocation()
            mLocationOverlay.isDrawAccuracyEnabled = true
            map.overlays.add(mLocationOverlay)
            mapController.setZoom(14.0)
        }

    }




    override fun onResume() {
        super.onResume()
        getInstance().load(binding.root.context, PreferenceManager.getDefaultSharedPreferences(binding.root.context))
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        val prefs = PreferenceManager.getDefaultSharedPreferences(binding.root.context)
        getInstance().save(binding.root.context, prefs)
        map.onPause()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}