package com.example.bookingdemoapp.di.module

import android.content.Context
import com.example.bookingdemoapp.data.api.APIService
import com.example.bookingdemoapp.data.repository.NewRecurringBookingRepository
import com.example.bookingdemoapp.di.BaseUrl
import com.example.bookingdemoapp.utils.AppConstant
import com.example.bookingdemoapp.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext appContext: Context): NetworkHelper =
        NetworkHelper(appContext)

    @BaseUrl
    @Provides
    fun provideBaseURL(): String = AppConstant.BASE_URL

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideAPIService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): APIService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        apiService: APIService
    ) = NewRecurringBookingRepository(apiService)
}