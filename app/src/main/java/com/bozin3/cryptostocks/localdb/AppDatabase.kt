package com.bozin3.cryptostocks.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bozin3.cryptostocks.localdb.dao.CryptoDao
import com.bozin3.cryptostocks.localdb.entity.Crypto

@Database(
    entities = [Crypto::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCryptoDao(): CryptoDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                "cryptodb")
                .build()
        }
    }
}