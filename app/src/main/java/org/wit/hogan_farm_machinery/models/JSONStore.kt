package org.wit.hogan_farm_machinery.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hogan_farm_machinery.helpers.*
import java.util.*
import kotlin.collections.ArrayList

val JSON_FILE = "tractors.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<TractorModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class JSONStore : TractorStore, AnkoLogger {

    val context: Context
    var tractors = mutableListOf<TractorModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<TractorModel> {
        return tractors
    }

    override fun create(tractor: TractorModel) {
        tractor.id = generateRandomId()
        tractors.add(tractor)
        serialize()
    }


    override fun update(tractor: TractorModel) {
        val tractorsList = findAll() as ArrayList<TractorModel>
        val foundTractor: TractorModel? = tractors.find { p -> p.id == tractor.id }
        if (foundTractor != null) {
            foundTractor.make = tractor.make
            foundTractor.price = tractor.price
            foundTractor.image = tractor.image
            foundTractor.radio1 = tractor.radio1
            foundTractor.radio2 = tractor.radio2
            foundTractor.category = tractor.category
        }
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