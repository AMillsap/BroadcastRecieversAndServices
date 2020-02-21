package com.example.broadcastrecieversandservices

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RandomObject(
    var stringOne : String,
    var stringTwo : String,
    var stringThree : String,
    var picture : String
) : Parcelable