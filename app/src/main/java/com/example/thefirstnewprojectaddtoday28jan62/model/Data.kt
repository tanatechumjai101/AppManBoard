package com.example.thefirstnewprojectaddtoday28jan62.model

import android.os.Parcel
import android.os.Parcelable
import android.text.Spanned
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("subject")
    var subject: String,
    @SerializedName("detail")
    var detail: String,
    @SerializedName("time")
    var time: String,
    @SerializedName("imageURI")
    var imageURI: String,
    @SerializedName("displayname")
    var displayname: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("id")
    var id: String
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