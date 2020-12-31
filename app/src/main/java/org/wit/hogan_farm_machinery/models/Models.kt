package org.wit.hogan_farm_machinery.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TractorModel(var id: Long = 0,
                        var make: String = "",
                        var price:  String = "",
                        var image: String = "",
                        var description: String = "",
                        var radio1: Boolean = false,
                        var radio2: Boolean = false,
                        var category: Boolean = true,
                        var lat : Double = 0.0,
                        var lng: Double = 0.0,
                        var zoom: Float = 0f
) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable



