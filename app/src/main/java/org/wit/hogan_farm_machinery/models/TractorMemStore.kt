package org.wit.hogan_farm_machinery.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class TractorMemStore : TractorStore, AnkoLogger {

    val tractors = ArrayList<TractorModel>()

    override fun findAll(): List<TractorModel> {
        return tractors
    }

    override fun create(tractor: TractorModel) {
        tractors.add(tractor)
        logAll();
    }
    fun logAll() {
        tractors.forEach{ info("${it}") }
    }
}