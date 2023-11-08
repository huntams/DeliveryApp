package com.example.deliveryapp.common.di

import com.example.deliveryapp.common.data.remote.repository.DeliveryRepository
import com.example.deliveryapp.common.data.remote.repository.DeliveryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule() {

    @Binds
    abstract fun bindDeliveryRepository(impl: DeliveryRepositoryImpl): DeliveryRepository
}