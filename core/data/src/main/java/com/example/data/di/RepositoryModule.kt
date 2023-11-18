package com.example.data.di

import com.example.data.repository.DeliveryDBRepositoryImpl
import com.example.data.repository.DeliveryRepositoryImpl
import com.example.database.repository.DeliveryDBRepository
import com.example.network.repository.DeliveryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule() {

    @Binds
    abstract fun bindDeliveryRepository(impl: DeliveryRepositoryImpl): DeliveryRepository

    @Binds
    abstract fun bindDeliveryDBRepository(impl: DeliveryDBRepositoryImpl): DeliveryDBRepository
}