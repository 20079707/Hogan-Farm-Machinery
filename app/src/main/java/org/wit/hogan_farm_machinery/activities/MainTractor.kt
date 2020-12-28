package org.wit.hogan_farm_machinery.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_tractor.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.helpers.readImage
import org.wit.hogan_farm_machinery.helpers.readImageFromPath
import org.wit.hogan_farm_machinery.helpers.showImagePicker
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.TractorModel



class MainTractor : AppCompatActivity(), AnkoLogger {

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

        if (intent.hasExtra("tractor_edit")) {
            edit = true
            tractor = intent.extras?.getParcelable("tractor_edit")!!
            tractorMake.setText(tractor.make)
            tractorModel.setText(tractor.model)
            tractorImage.setImageBitmap(readImageFromPath(this, tractor.image))
            btnAdd.setText(R.string.button_updateTractor)
            chooseImage.setText(R.string.button_updateImage)
        }
        

        btnAdd.setOnClickListener {
            tractor.make = tractorMake.text.toString()
            tractor.model = tractorModel.text.toString()

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

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radio_for_sale ->
                    if (checked) {
                        tractor.radio1 = radio_for_sale.text.toString()
                    }
                R.id.radio_wanted ->
                    if (checked) {
                        tractor.radio2 = radio_wanted.text.toString()
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
        }
    }
}



