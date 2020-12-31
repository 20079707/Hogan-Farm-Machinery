package org.wit.hogan_farm_machinery.activities.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.tractor_card.*
import kotlinx.android.synthetic.main.activity_tractor_maps.*
import kotlinx.android.synthetic.main.content_tractor_list.*
import kotlinx.android.synthetic.main.tractor_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.activities.createAd.MainTractor
import org.wit.hogan_farm_machinery.activities.adapter.TractorAdapter
import org.wit.hogan_farm_machinery.activities.adapter.TractorListener
import org.wit.hogan_farm_machinery.activities.maps.TractorMapsActivity
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.TractorModel

class TractorList : AppCompatActivity(), TractorListener {

    lateinit var app: MainApp
    var tractor = TractorModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tractor_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val navigationView = findViewById<View>(R.id.nav) as BottomNavigationView


        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.list ->{
                    startActivity<TractorList>()
                }
                R.id.home -> {
                        startActivity<MainTractor>()
                }
                R.id.map ->{
                        startActivity<TractorMapsActivity>()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = TractorAdapter(app.tractors.findAll(), this)
        loadTractors()


        fab.setOnClickListener {
            startActivityForResult<MainTractor>(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            /*R.id.item_add -> startActivityForResult<MainTractor>(0)*/
            R.id.item_map -> startActivity<TractorMapsActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDeleteClick(item: MenuItem) {
        when (item.itemId) {
            R.id.button_delete -> app.tractors.delete(tractor)
        }
    }

    override fun onTractorClick(tractor: TractorModel) {
        startActivityForResult(intentFor<MainTractor>().putExtra("tractor_edit", tractor), 0)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadTractors()
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun loadTractors() {
        showTractors(app.tractors.findAll())
    }

    fun showTractors (tractors: List<TractorModel>) {
        recyclerView.adapter = TractorAdapter(tractors, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}