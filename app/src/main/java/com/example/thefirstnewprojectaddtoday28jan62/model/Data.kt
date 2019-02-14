package com.example.thefirstnewprojectaddtoday28jan62.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("subject")
    val subject: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("imageURI")
    val imageURI: String,
    @SerializedName("displayname")
    val displayname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(subject)
        parcel.writeString(detail)
        parcel.writeString(time)
        parcel.writeString(imageURI)
        parcel.writeString(displayname)
        parcel.writeString(email)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }

}