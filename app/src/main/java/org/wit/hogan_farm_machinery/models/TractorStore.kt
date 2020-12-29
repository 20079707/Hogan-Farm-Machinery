package org.wit.hogan_farm_machinery.models

interface TractorStore {
    fun findAll(): List<TractorModel>
    fun create(tractor: TractorModel)
    fun update(tractor: TractorModel)

}