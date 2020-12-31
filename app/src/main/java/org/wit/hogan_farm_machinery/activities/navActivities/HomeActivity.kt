package org.wit.hogan_farm_machinery.activities.navActivities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_activity_home.*
import kotlinx.android.synthetic.main.content_activity_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.activities.create_advert.PlaceAdActivity
import org.wit.hogan_farm_machinery.databinding.ActivityHomeBinding
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.TractorModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var app: MainApp
    var tractor = TractorModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        setSupportActionBar(binding.toolbar)


        getStarted.setOnClickListener{
            startActivity<PlaceAdActivity>()
        }

        // determines the functionality of nav bar at the bottom of the screen
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
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}