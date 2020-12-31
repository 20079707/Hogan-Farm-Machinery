package org.wit.hogan_farm_machinery.activities.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_activity_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.activities.adapter.TractorAdapter
import org.wit.hogan_farm_machinery.activities.create_advert.PlaceAdActivity
import org.wit.hogan_farm_machinery.activities.adapter.TractorListener
import org.wit.hogan_farm_machinery.activities.home.HomeActivity
import org.wit.hogan_farm_machinery.activities.maps.ShowMapsActivity
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.TractorModel

class ListActivity : AppCompatActivity(), TractorListener {

    lateinit var app: MainApp
    var tractor = TractorModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

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

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = TractorAdapter(app.tractors.findAll(), this)
        loadTractors()


        fab.setOnClickListener {
            startActivityForResult<PlaceAdActivity>(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onTractorClick(tractor: TractorModel) {
        startActivityForResult(intentFor<PlaceAdActivity>().putExtra("tractor_edit", tractor), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadTractors()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadTractors() {
        showTractors(app.tractors.findAll())
    }

    private fun showTractors (tractors: List<TractorModel>) {
        recyclerView.adapter = TractorAdapter(tractors, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}