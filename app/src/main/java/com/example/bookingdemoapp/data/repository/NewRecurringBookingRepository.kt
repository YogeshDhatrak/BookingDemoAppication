package com.example.bookingdemoapp.data.repository

import com.example.bookingdemoapp.data.api.APIService
import com.example.bookingdemoapp.data.model.Children
import com.example.bookingdemoapp.ui.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class NewRecurringBookingRepository @Inject constructor(private val apiService: APIService) :
    BaseRepository() {

    fun getChildren(): Flow<List<Children>> {
        return flow {
            emit(apiService.getChildren())
        }.map {
            val result = it.body()
            Timber.e("getChildren--->$result")
            result?.data?.children ?: emptyList()
        }
    }
}