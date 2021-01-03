package org.wit.hogan_farm_machinery.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hogan_farm_machinery.models.FireStore
import org.wit.hogan_farm_machinery.models.JSONStore
import org.wit.hogan_farm_machinery.models.MemStore
import org.wit.hogan_farm_machinery.models.TractorStore

class MainApp : Application(), AnkoLogger {

    lateinit var tractors: TractorStore

    override fun onCreate() {
        super.onCreate()
        //tractors = JSONStore(applicationContext)

        tractors = FireStore(applicationContext) //store tractors in firebase db
        info("Hogan FM App started")
    }
}