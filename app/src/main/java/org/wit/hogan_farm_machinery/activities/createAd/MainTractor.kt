package org.wit.hogan_farm_machinery.activities.createAd

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tractor.*
import kotlinx.android.synthetic.main.activity_tractor.view.*
import kotlinx.android.synthetic.main.tractor_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.activities.maps.MapsActivity
import org.wit.hogan_farm_machinery.helpers.readImage
import org.wit.hogan_farm_machinery.helpers.readImageFromPath
import org.wit.hogan_farm_machinery.helpers.showImagePicker
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.Location
import org.wit.hogan_farm_machinery.models.TractorModel



class MainTractor : AppCompatActivity(), AnkoLogger {

    val LOCATION_REQUEST = 2
    var location = Location(52.245696, -7.139102, 15f)
    private var tractor = TractorModel()
    private lateinit var app: MainApp
    private val imageRequest = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tractor)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Tractor Activity started..")


        app = application as MainApp
        var edit = false


        val data = resources.getStringArray(R.array.category_array)
        val adapter = ArrayAdapter(this, R.layout.spinner_item_selected, data)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.categorySpinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(this@MainTractor, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        if (intent.hasExtra("tractor_edit")) {
            edit = true
            tractor = intent.extras?.getParcelable("tractor_edit")!!
            tractorMake.setText(tractor.make)
            tractorPrice.setText(tractor.price)
            tractorDescription.setText(tractor.description)
            tractorImage.setImageBitmap(readImageFromPath(this, tractor.image))
            btnAdd.setText(R.string.button_updateTractor)
            chooseImage.setText(R.string.button_updateImage)
        }
        

        btnAdd.setOnClickListener {
            tractor.make = tractorMake.text.toString()
            tractor.price = tractorPrice.text.toString()
            tractor.description = tractorDescription.text.toString()
            if (tractor.make.isEmpty()) {
                toast(R.string.enter_tractorMake)
            } else {
                if (edit) {
                    app.tractors.update(tractor.copy())
                } else {
                    app.tractors.create(tractor.copy())
                }
            }
            info("add Button Pressed: $tractor")
            setResult(RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, imageRequest)
        }
        selectLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (tractor.zoom != 0f) {
                location.lat =  tractor.lat
                location.lng = tractor.lng
                location.zoom = tractor.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    fun onRadioButtonClicked(view: View) {

        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            val edit = false
            when (view.getId()) {
                R.id.radio_for_sale ->
                    if (checked) {
                        tractor.radio1 = true
                        Toast.makeText(applicationContext,"For Sale selected",
                                Toast.LENGTH_SHORT).show()

                    } else {
                        tractor.radio1 = false

                    }
                R.id.radio_wanted ->
                    if (checked) {
                        tractor.radio2 = true
                        Toast.makeText(applicationContext,"Wanted selected",
                                Toast.LENGTH_SHORT).show()
                    }else {
                        if(edit) {
                            tractor.radio2 = false
                        }
                        tractor.radio2 = false

                    }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tractor, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            imageRequest -> {
                if (data != null) {
                    tractor.image = data.data.toString()
                    tractorImage.setImageBitmap(readImage(this, resultCode, data))
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")
                    if (location != null) {
                        tractor.lat = location.lat
                    }
                    if (location != null) {
                        tractor.lng = location.lng
                    }
                    if (location != null) {
                        tractor.zoom = location.zoom
                    }
                }
            }
        }
    }
}



