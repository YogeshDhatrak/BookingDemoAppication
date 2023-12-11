package com.example.bookingdemoapp.di.component

import com.example.bookingdemoapp.data.api.APIService
import com.example.bookingdemoapp.data.repository.NewRecurringBookingRepository
import com.example.bookingdemoapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun getAPIService(): APIService

    fun getNewRecurringBookingRepository(): NewRecurringBookingRepository
}