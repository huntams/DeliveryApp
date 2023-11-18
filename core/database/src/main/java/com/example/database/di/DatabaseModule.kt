package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.db.DeliveryDAO
import com.example.database.db.DeliveryDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "DeliveryDatabase"

    @Provides
    @Singleton
    fun provideNotesDatabase(
        @ApplicationContext context: Context,
    ): DeliveryDB {
        return Room.databaseBuilder(
            context,
            DeliveryDB::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesDAO(db: DeliveryDB): DeliveryDAO {
        return db.deliveryDAO()
    }
}