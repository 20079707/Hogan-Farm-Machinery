package org.wit.hogan_farm_machinery.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class TractorMemStore : TractorStore, AnkoLogger {

    private val tractors = ArrayList<TractorModel>()

    override fun findAll(): List<TractorModel> {
        return tractors
    }

    override fun create(tractor: TractorModel) {
        tractor.id = getId()
        tractors.add(tractor)
        logAll()
    }

    override fun update(tractor: TractorModel) {
        val foundTractor: TractorModel? = tractors.find { p -> p.id == tractor.id }
        if (foundTractor != null) {
            foundTractor.make = tractor.make
            foundTractor.price = tractor.price
            foundTractor.image = tractor.image
            foundTractor.radio1 = tractor.radio1
            foundTractor.radio2 = tractor.radio2
            foundTractor.category = tractor.category
            logAll()
        }
    }

    override fun delete(tractor: TractorModel) {
        tractors.remove(tractor)
    }

    private fun logAll() {
        tractors.forEach{ info("$it") }
    }
}