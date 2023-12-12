package com.example.bookingdemoapp.data.api

import com.example.bookingdemoapp.data.model.AvailableRoomResponse
import com.example.bookingdemoapp.data.model.BookingRooms
import com.example.bookingdemoapp.data.model.ChildrenResponse
import com.example.bookingdemoapp.utils.AppConstant.GET_ACCESS_TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface APIService {
    //@Headers("x-collection-access-token:$GET_ACCESS_TOKEN")
    @GET("076c0cb9-c60e-48eb-a447-77e85b700d94")
    suspend fun getChildren(@Header("x-collection-access-token") header: String = GET_ACCESS_TOKEN): Response<ChildrenResponse>


    @Headers("x-collection-access-token:$GET_ACCESS_TOKEN")
    @GET("{available_room_id}")
    suspend fun getAvailableRooms(@Path("available_room_id") availableRoomId: String): Response<AvailableRoomResponse>
}
