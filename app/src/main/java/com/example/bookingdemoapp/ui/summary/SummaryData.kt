package com.example.bookingdemoapp.ui.summary

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SummaryData(
    val childrenName: String,
    val roomName: String,
    val startDate:String,
    val endDate:String,
    val selectedDays:String,
    val bookingReferenceNo:String
) : Parcelable