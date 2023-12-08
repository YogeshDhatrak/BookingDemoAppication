package com.example.bookingdemoapp.data.api

import com.example.bookingdemoapp.data.model.BookingRooms
import com.example.bookingdemoapp.data.model.Children
import com.example.bookingdemoapp.utils.AppConstant.GET_ACCESS_TOKEN
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Singleton

@Singleton
interface APIService {
    @Headers("x-collection-access-token: $GET_ACCESS_TOKEN")
    @GET("076c0cb9-c60e-48eb-a447-77e85b700d94")
    suspend fun getChildren():List<Children>


    @Headers("x-collection-access-token: $GET_ACCESS_TOKEN")
    @GET("076c0cb9-c60e-48eb-a447-77e85b700d94")
    suspend fun getAvailableRooms():List<BookingRooms>
}