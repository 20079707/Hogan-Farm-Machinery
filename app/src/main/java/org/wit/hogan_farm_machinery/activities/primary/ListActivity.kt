package org.wit.hogan_farm_machinery.activities.primary

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_activity_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.adapter.TractorAdapter
import org.wit.hogan_farm_machinery.activities.create.PlaceAdActivity
import org.wit.hogan_farm_machinery.adapter.TractorListener
import org.wit.hogan_farm_machinery.databinding.ActivityListBinding
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.TractorModel
import org.wit.hogan_farm_machinery.activities.authentication.LogInActivity
import org.wit.hogan_farm_machinery.activities.authentication.WelcomeActivity

class ListActivity : AppCompatActivity(), TractorListener {

    private lateinit var binding: ActivityListBinding
    lateinit var app: MainApp
    var tractor = TractorModel()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        app = application as MainApp
        firebaseAnalytics = Firebase.analytics


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

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = TractorAdapter(app.tractors.findAll(), this)
        loadTractors()


        //if floating action button is pressed
        fab.setOnClickListener {
            startActivityForResult<PlaceAdActivity>(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                FirebaseAuth.getInstance().signOut()
                app.tractors.clear()
                val intent = Intent(this@ListActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()

                return true
            }
        }
        return false
    }


    //if card is selected go to...
    override fun onTractorClick(tractor: TractorModel) {
        startActivityForResult(intentFor<PlaceAdActivity>().putExtra("tractor_edit", tractor), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadTractors()
        super.onActivityResult(requestCode, resultCode, data)
    }

    //finds all tractors from db and display them
    private fun loadTractors() {
        showTractors(app.tractors.findAll())
    }


    private fun showTractors(tractors: List<TractorModel>) {
        recyclerView.adapter = TractorAdapter(tractors, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}