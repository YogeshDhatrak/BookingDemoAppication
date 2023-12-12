package com.example.bookingdemoapp.utils

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


fun getDuration(sDate: String, eDate: String): Long {
    val myFormat = SimpleDateFormat("dd MMM yyyy")
    val date1 = myFormat.parse(sDate)
    val date2 = myFormat.parse(eDate)
    val diff = date2.time - date1.time
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1
}