package org.wit.hogan_farm_machinery.activities.create

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_create_advert.*
import kotlinx.android.synthetic.main.activity_create_advert.view.*
import org.jetbrains.anko.*
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.helpers.readImage
import org.wit.hogan_farm_machinery.helpers.showImagePicker
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.Location
import org.wit.hogan_farm_machinery.models.TractorModel


class PlaceAdActivity : AppCompatActivity(), AnkoLogger {

    private var tractor = TractorModel()
    private lateinit var app: MainApp
    private val imageRequest = 1
    private val locationRequest = 2
    var edit = false

    // Creating PlaceAdvert View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Linking Layout
        setContentView(R.layout.activity_create_advert)
        toolbarAdd.title = title
        //Setting the Toolbar
        setSupportActionBar(toolbarAdd)
        info("Tractor Activity started..")


        app = application as MainApp
        edit = false


        //spinner Function
        val data = resources.getStringArray(R.array.category_array)
        val adapter = ArrayAdapter(this, R.layout.spinner_item_selected, data)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.categorySpinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {
                Toast.makeText(this@PlaceAdActivity,
                    parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG).show()
                tractor.category = categorySpinner.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        // setting edit values for tractor
        if (intent.hasExtra("tractor_edit")) {
            edit = true
            tractor = intent.extras?.getParcelable("tractor_edit")!!
            tractorMake.setText(tractor.make)
            tractorPrice.setText(tractor.price)
            tractorYear.setText(tractor.year)
            tractorDescription.setText(tractor.description)
            Glide.with(this).load(tractor.image).into(tractorImage);
            btnAdd.setText(R.string.button_updateTractor)
            chooseImage.setText(R.string.button_updateImage)
        }

        // button which saves the values in the fields
        btnAdd.setOnClickListener {
            tractor.make = tractorMake.text.toString()
            tractor.price = tractorPrice.text.toString()
            tractor.year = tractorYear.text.toString()
            tractor.description = tractorDescription.text.toString()
            if (tractor.make.isEmpty()) {
                toast(R.string.prompt_tractorMake)
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

        // adding the choose image functionality to the select image button
        chooseImage.setOnClickListener {
            showImagePicker(this, imageRequest)
        }

        // adding the add location functionality to the select set location button
        selectLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (tractor.location.zoom != 0f) {
                location.lat = tractor.location.lat
                location.lng = tractor.location.lng
                location.zoom = tractor.location.zoom
            }
            startActivityForResult(intentFor<AddMapsActivity>().putExtra("location", location),
                locationRequest)
        }
    }

    // radio button function
    fun onRadioButtonClicked(view: View) {

        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_for_sale ->
                    if (checked) {
                        tractor.radio1 = true
                        Toast.makeText(applicationContext, "For Sale selected",
                            Toast.LENGTH_SHORT).show()

                    } else {
                        tractor.radio1 = false

                    }
                R.id.radio_wanted ->
                    if (checked) {
                        tractor.radio2 = true
                        Toast.makeText(applicationContext, "Wanted selected",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        tractor.radio2 = false

                    }
            }
        }

    }

    // determines which buttons are displayed in toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_advert, menu)

        // sets delete button for edit view
        if (edit && menu != null) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    // gives toolbar functions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.tractors.delete(tractor)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // needed to save the image and location
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
            locationRequest -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")
                    if (location != null) {
                        tractor.location.lat = location.lat
                    }
                    if (location != null) {
                        tractor.location.lng = location.lng
                    }
                    if (location != null) {
                        tractor.location.zoom = location.zoom
                    }
                }
            }
        }
    }
}









