package com.bozin3.cryptostocks.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bozin3.cryptostocks.localdb.entity.Crypto

@Dao
interface CryptoDao {

    @Query("select * from crypto")
    fun getAllData(): LiveData<List<Crypto>>

    @Query("select * from crypto where id=:id")
    fun getCryptoById(id: Long): LiveData<Crypto>

    @Query("select * from crypto where name like :query")
    fun filterCryptoData(query: String): LiveData<List<Crypto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( videos: List<Crypto>)

}