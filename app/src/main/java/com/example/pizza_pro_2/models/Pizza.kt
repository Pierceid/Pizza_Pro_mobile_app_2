package com.example.pizza_pro_2.models

import android.os.Parcel
import android.os.Parcelable
import java.util.UUID

data class Pizza(
    val name: String?,
    val description: String?,
    val imageSource: Int,
    val rating: Double,
    val time: Int,
    val calories: Int,
    val cost: Double,
    var count: Int = 0,
    val id: String? = UUID.randomUUID().toString(),
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(imageSource)
        parcel.writeDouble(rating)
        parcel.writeInt(time)
        parcel.writeInt(calories)
        parcel.writeDouble(cost)
        parcel.writeInt(count)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pizza> {
        override fun createFromParcel(parcel: Parcel): Pizza {
            return Pizza(parcel)
        }

        override fun newArray(size: Int): Array<Pizza?> {
            return arrayOfNulls(size)
        }
    }
}