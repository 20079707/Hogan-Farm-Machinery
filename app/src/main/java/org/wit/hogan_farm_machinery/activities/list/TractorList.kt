package org.wit.hogan_farm_machinery.activities.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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


        toolbar.title = title
        setSupportActionBar(toolbar)


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


    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.item_add -> startActivityForResult<MainTractor>(0)

            /*R.id.item_add -> startActivityForResult<MainTractor>(0)*/
            R.id.item_map -> startActivity<TractorMapsActivity>()

        }
        return super.onOptionsItemSelected(item)
    }*/

    override fun onDeleteClick(button: ImageButton) {
        when (button.id) {
            R.id.button_delete -> {
            app.tractors.delete(tractor)
            }
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