package org.wit.hogan_farm_machinery.models

interface TractorStore {
    fun findById(id:Long) : TractorModel?
    fun findAll(): List<TractorModel>
    fun create(tractor: TractorModel)
    fun update(tractor: TractorModel)
    fun delete(tractor: TractorModel)
    fun clear()


}