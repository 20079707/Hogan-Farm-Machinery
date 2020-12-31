package org.wit.hogan_farm_machinery.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hogan_farm_machinery.helpers.*
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "tractors.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
val listType: Type = object : TypeToken<java.util.ArrayList<TractorModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class JSONStore(val context: Context) : TractorStore, AnkoLogger {

    private var tractors = mutableListOf<TractorModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findById(id: Long): TractorModel? {
        return tractors.find { it.id == id }
    }

    override fun findAll(): MutableList<TractorModel> {
        return tractors
    }

    // saves values
    override fun create(tractor: TractorModel) {
        tractor.id = generateRandomId()
        tractors.add(tractor)
        serialize()
    }

    // finds value saved and replaces it with updated values
    override fun update(tractor: TractorModel) {
        val tractorsList = findAll() as ArrayList<TractorModel>
        val foundTractor: TractorModel? = tractors.find { p -> p.id == tractor.id }
        if (foundTractor != null) {
            foundTractor.make = tractor.make
            foundTractor.price = tractor.price
            foundTractor.image = tractor.image
            foundTractor.description = tractor.description
            foundTractor.radio1 = tractor.radio1
            foundTractor.radio2 = tractor.radio2
            foundTractor.category = tractor.category
            foundTractor.lat = tractor.lat
            foundTractor.lng = tractor.lng
            foundTractor.zoom = tractor.zoom
        }
        serialize()
    }

    // deletes values from db
    override fun delete(tractor: TractorModel) {
        tractors.remove(tractor)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(tractors, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        tractors = Gson().fromJson(jsonString, listType)
    }
}