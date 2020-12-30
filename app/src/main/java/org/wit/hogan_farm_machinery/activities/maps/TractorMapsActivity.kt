package org.wit.hogan_farm_machinery.activities.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_tractor_maps.*
import org.wit.hogan_farm_machinery.databinding.ActivityTractorMapsBinding
import org.wit.hogan_farm_machinery.helpers.readImageFromPath
import org.wit.hogan_farm_machinery.main.MainApp


class TractorMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityTractorMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTractorMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMaps)
        app = application as MainApp

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            configureMap()
        }


        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        val tractor = app.tractors.findById(tag)
        currentMake.text = tractor!!.make
        currentPrice.text = tractor.price
        imageView.setImageBitmap(readImageFromPath(this@TractorMapsActivity, tractor.image))
        return true

    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.tractors.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.make).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

}