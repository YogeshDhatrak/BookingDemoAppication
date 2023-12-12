package com.example.bookingdemoapp.ui.base

abstract class BaseRepository {
    open fun errorHandling(errorCode: String): String {
        return when (errorCode) {
            "404" -> "Not Found"
            "500" -> "Please try after sometime"
            else -> "Something went wrong,Please try after sometime"
        }
    }
}