package org.wit.hogan_farm_machinery.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hogan_farm_machinery.models.TractorMemStore
import org.wit.hogan_farm_machinery.models.TractorModel

class MainApp : Application(), AnkoLogger {

    val tractors = TractorMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Hogan FM App started")
    }
}