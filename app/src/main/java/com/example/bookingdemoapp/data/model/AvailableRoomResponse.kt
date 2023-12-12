package com.example.bookingdemoapp.data.model

import com.google.gson.annotations.SerializedName

data class AvailableRoomResponse(
    @SerializedName("data")
    val data: BookingRoomList,
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("version")
    val version: Int
)