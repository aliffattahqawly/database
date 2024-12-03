package com.example.loginsignupfrd.model

import android.os.Parcel
import android.os.Parcelable

data class UserData(
    val id: String? = null,
    val username: String? = null,
    val password: String? = null,
    var profileImageUrl: String? = null,
    var backgroundImageUrl: String? = null,
    var name: String? = null,
    var status: String? = null,
    var profession: String? = null,
    var phoneNumber: String? = null,
    var origin: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(profileImageUrl)
        parcel.writeString(backgroundImageUrl)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(profession)
        parcel.writeString(phoneNumber)
        parcel.writeString(origin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }
}