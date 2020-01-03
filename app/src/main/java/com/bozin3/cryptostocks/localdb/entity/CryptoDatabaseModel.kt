package com.bozin3.cryptostocks.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto")
data class CryptoDatabaseModel(
    @PrimaryKey val id: Long,
    @ColumnInfo val symbol: String,
    @ColumnInfo val name: String,
    @ColumnInfo val price: Float,
    @ColumnInfo(name = "change_hour") val percentageChangeHour: Float,
    @ColumnInfo(name = "change_day") val percentageChangeDay: Float,
    @ColumnInfo(name = "change_week") val percentageChangeWeek: Float,
    @ColumnInfo(name = "market_cap") val marketCap: Float?,
    @ColumnInfo(name = "total_supply") val totalSupply: Float?,
    @ColumnInfo(name = "max_supply") val maxSupply: Float?
)