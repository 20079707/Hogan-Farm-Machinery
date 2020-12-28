package org.wit.hogan_farm_machinery.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TractorModel(var id: Long = 0,
                        var make: String = "",
                        var model: String = "",
                        var image: String = "",
                        var radio1: String = "",
                        var radio2: String = ""
) : Parcelable

