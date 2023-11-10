package com.example.common.utils.di

import com.example.common.utils.data.remote.repository.DeliveryRepository
import com.example.common.utils.data.remote.repository.DeliveryRepositoryImpl
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