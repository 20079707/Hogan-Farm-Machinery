package org.wit.hogan_farm_machinery.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TractorModel(@PrimaryKey(autoGenerate = true)
                        var fbId : String = "",
                        var id: Long = 0,
                        var make: String = "",
                        var price:  String = "",
                        var image: String = "",
                        var description: String = "",
                        var radio1: Boolean = false,
                        var radio2: Boolean = false,
                        var category: Boolean = true,
                        @Embedded var location : Location = Location()): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable



