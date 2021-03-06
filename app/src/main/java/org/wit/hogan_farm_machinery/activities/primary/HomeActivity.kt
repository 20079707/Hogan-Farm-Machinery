package org.wit.hogan_farm_machinery.activities.primary

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_activity_home.*
import kotlinx.android.synthetic.main.content_activity_list.*
import org.jetbrains.anko.startActivity
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.activities.create.PlaceAdActivity
import org.wit.hogan_farm_machinery.databinding.ActivityHomeBinding
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.TractorModel
import org.wit.hogan_farm_machinery.activities.authentication.LogInActivity
import org.wit.hogan_farm_machinery.activities.authentication.WelcomeActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var app: MainApp
    var tractor = TractorModel()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        setSupportActionBar(binding.toolbar)
        firebaseAnalytics = Firebase.analytics


        getStarted.setOnClickListener {
            startActivity<PlaceAdActivity>()
        }


        // determines the functionality of nav bar at the bottom of the screen
        val navigationView = findViewById<View>(R.id.nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.list -> {
                    startActivity<ListActivity>()
                }
                R.id.home -> {
                    startActivity<HomeActivity>()
                }
                R.id.map -> {
                    startActivity<ShowMapsActivity>()
                }
                R.id.item_logout -> {
                    startActivity<LogInActivity>()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    //inflates buttons on menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    // menu button functionality
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                FirebaseAuth.getInstance().signOut()
                app.tractors.clear()
                val intent = Intent(this@HomeActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()

                return true
            }
        }
        return false
    }
}