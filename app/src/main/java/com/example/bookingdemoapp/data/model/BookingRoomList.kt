package com.example.bookingdemoapp.data.model

import com.google.gson.annotations.SerializedName

data class BookingRoomList(
    @SerializedName("bookingRooms")
    val bookingRooms: List<BookingRoom>
)