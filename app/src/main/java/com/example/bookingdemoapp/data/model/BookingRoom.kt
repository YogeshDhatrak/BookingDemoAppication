package com.example.bookingdemoapp.data.model

data class BookingRoom(
    val endTime: String,
    val fee: String,
    val feeFkey: String,
    val roomFkey: String,
    val roomName: String,
    val startTime: String
)