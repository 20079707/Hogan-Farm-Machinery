package org.wit.hogan_farm_machinery.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.DataInput

@Parcelize
data class TractorModel(var id: Long = 0,
                        var make: String = "",
                        var price:  String = "",
                        var image: String = "",
                        var radio1: String = "",
                        var radio2: String = "",
                        var category: String = ""
) : Parcelable



