package com.example.bookingdemoapp.data.repository

import com.example.bookingdemoapp.data.api.APIService
import com.example.bookingdemoapp.ui.base.BaseRepository
import com.example.bookingdemoapp.ui.newrecurringbooking.BookingRoomsModel
import com.example.bookingdemoapp.ui.newrecurringbooking.ChildrenDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class NewRecurringBookingRepository @Inject constructor(
    private val apiService: APIService
) : BaseRepository() {

    suspend fun getChildren(): Flow<List<ChildrenDetailsModel>> {
        return flow {
            emit(apiService.getChildren())
        }.map { response ->
            val result = response.body()
            Timber.e("getChildren--->$result")
            result?.data?.children?.map {
                ChildrenDetailsModel(
                    age = it.age,
                    fkey = it.fkey,
                    availableRoomsId = it.availableRoomsId,
                    fullName = it.fullName
                )
            } ?: emptyList()
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAvailableRooms(availableRoomsId: String): Flow<List<BookingRoomsModel>> {
        return flow {
            emit(apiService.getAvailableRooms(availableRoomsId))
        }.map { response ->
            val result = response.body()
            Timber.e("getAvailableRooms--->$result")
            result?.data?.bookingRooms?.map {
                BookingRoomsModel(
                    fee = it.fee,
                    endTime = it.endTime,
                    feeFkey = it.feeFkey,
                    roomFkey = it.roomFkey,
                    roomName = it.roomName,
                    startTime = it.startTime
                )
            } ?: emptyList()
        }.flowOn(Dispatchers.IO)
    }

}