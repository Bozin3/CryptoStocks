package com.bozin3.cryptostocks.di

import android.app.Application
import com.bozin3.cryptostocks.localdb.AppDatabase
import com.bozin3.cryptostocks.localdb.dao.CryptoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Provides
    @Singleton
    fun provideCryptoDao(db: AppDatabase): CryptoDao {
        return db.getCryptoDao()
    }
}