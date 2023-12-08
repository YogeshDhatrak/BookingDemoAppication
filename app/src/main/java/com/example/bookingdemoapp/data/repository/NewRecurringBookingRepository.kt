package com.example.bookingdemoapp.data.repository

import com.example.bookingdemoapp.data.api.APIService
import com.example.bookingdemoapp.ui.base.BaseRepository
import javax.inject.Inject

class NewRecurringBookingRepository @Inject constructor(private val apiService: APIService):BaseRepository(){

}