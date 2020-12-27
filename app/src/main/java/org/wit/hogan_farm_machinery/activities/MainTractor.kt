package org.wit.hogan_farm_machinery.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_tractor.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.TractorModel



class MainTractor : AppCompatActivity(), AnkoLogger {

    var tractor = TractorModel()
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tractor)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Placemark Activity started..")

        app = application as MainApp
        var edit = false

        if (intent.hasExtra("tractor_edit")) {
            edit = true
            tractor = intent.extras?.getParcelable<TractorModel>("tractor_edit")!!
            tractorMake.setText(tractor.make)
            tractorModel.setText(tractor.model)
            btnAdd.setText(R.string.button_updateTractor)
        }

        btnAdd.setOnClickListener() {
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
            info("add Button Pressed: ${tractor}")
            setResult(RESULT_OK)
            finish()

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

}



