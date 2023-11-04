package com.example.deliveryapp.di

import android.content.Context
import com.example.deliveryapp.data.DeliveryApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://themealdb.com/api/json/v1/1/"


    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        json: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(json)
            .build()
    }

    @Provides
    @Singleton
    fun provideJsonConverter(): Converter.Factory {
        return Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideDeliveryApiService(retrofit: Retrofit): DeliveryApiService {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

}