package org.wit.hogan_farm_machinery.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.content_all_maps.*
import org.jetbrains.anko.startActivity
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.databinding.ActivityAllMapsBinding
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.TractorModel
import org.wit.hogan_farm_machinery.activities.authentication.LogInActivity
import org.wit.hogan_farm_machinery.activities.authentication.WelcomeActivity


class ShowMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityAllMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    // lists all locations of tractors created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMaps)
        app = application as MainApp
        firebaseAnalytics = Firebase.analytics

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            configureMap()
        }

        val navigationView = findViewById<View>(R.id.nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.list ->{
                    startActivity<ListActivity>()
                }
                R.id.home -> {
                    startActivity<HomeActivity>()
                }
                R.id.map ->{
                    startActivity<ShowMapsActivity>()
                }
                R.id.item_logout ->{
                    startActivity<LogInActivity>()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout ->{
                FirebaseAuth.getInstance().signOut()
                app.tractors.clear()
                val intent = Intent(this@ShowMapsActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()

                return true
            }
        }
        return false
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tractor = marker.tag as TractorModel
        currentMake.text = tractor.make
        currentPrice.text = tractor.price
        Glide.with(this).load(tractor.image).into(imageView);
        return true

    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.tractors.findAll().forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.make).position(loc)
            map.addMarker(options).tag = it
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
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