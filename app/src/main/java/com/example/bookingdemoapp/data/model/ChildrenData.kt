package com.example.bookingdemoapp.data.model

import com.google.gson.annotations.SerializedName

data class ChildrenData(
    @SerializedName("children")
    val children: List<Children>
)