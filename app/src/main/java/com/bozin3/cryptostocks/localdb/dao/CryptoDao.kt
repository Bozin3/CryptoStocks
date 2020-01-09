package com.bozin3.cryptostocks.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bozin3.cryptostocks.localdb.entity.CryptoDatabaseModel

@Dao
interface CryptoDao {

    @Query("select * from crypto")
    fun getAllData(): LiveData<List<CryptoDatabaseModel>>

    @Query("select * from crypto where id=:id")
    fun getCryptoById(id: Int): LiveData<CryptoDatabaseModel>

    @Query("select * from crypto where name like :query")
    fun filterCryptoData(query: String): List<CryptoDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( videos: List<CryptoDatabaseModel>)

}