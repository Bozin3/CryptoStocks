package com.bozin3.cryptostocks.utils

import com.bozin3.cryptostocks.localdb.entity.CryptoDatabaseModel
import com.bozin3.cryptostocks.models.CryptoNetworkModel
import com.bozin3.cryptostocks.models.CryptoDomainModel
import timber.log.Timber

fun List<CryptoNetworkModel>.asDomainModel(): List<CryptoDomainModel> {
    return this.map {
        CryptoDomainModel(
            id = it.id,
            symbol = it.symbol,
            name = it.name,
            price = it.quote.usd.price,
            percentageChangeDay = it.quote.usd.percentChangeDay,
            percentageChangeHour = it.quote.usd.percentChangeHour,
            percentageChangeWeek = it.quote.usd.percentChangeWeek
        )
    }
}

fun List<CryptoNetworkModel>.asDatabaseModel(): List<CryptoDatabaseModel> {
    return this.map {
        CryptoDatabaseModel(
            id = it.id,
            symbol = it.symbol,
            name = it.name,
            price = it.quote.usd.price,
            percentageChangeDay = it.quote.usd.percentChangeDay,
            percentageChangeHour = it.quote.usd.percentChangeHour,
            percentageChangeWeek = it.quote.usd.percentChangeWeek,
            marketCap = it.quote.usd.marketCap,
            totalSupply = it.totalSupply,
            maxSupply = it.maxSupply
        )
    }
}

suspend fun List<CryptoDatabaseModel>.toDomainModel(): List<CryptoDomainModel> {
    return this.map {
        Timber.d("transforming db allData to domain model : ${Thread.currentThread().name}")
        it.toDomainModel()
    }
}

suspend fun CryptoDatabaseModel.toDomainModel(): CryptoDomainModel {
    return CryptoDomainModel(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        price = this.price,
        percentageChangeDay = this.percentageChangeDay,
        percentageChangeHour = this.percentageChangeHour,
        percentageChangeWeek = this.percentageChangeWeek
    )
}