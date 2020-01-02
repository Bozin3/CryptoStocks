package com.bozin3.cryptostocks.utils

import com.bozin3.cryptostocks.network.CryptoNetworkModel
import com.bozin3.cryptostocks.models.CryptoDomainModel

fun List<CryptoNetworkModel>.asDomainModel(): List<CryptoDomainModel> {
    return this.map {
        CryptoDomainModel(
            id = it.id,
            symbol = it.symbol,
            name = it.name,
            price = it.quote.usd.price,
            percentageDay = it.quote.usd.percentChangeDay,
            percentageHour = it.quote.usd.percentChangeHour,
            percentageWeek = it.quote.usd.percentChangeWeek
        )
    }
}

//fun List<CryptoNetworkModel>.asDatabaseModel(): List<CryptoDatabaseModel> {
//////    return this.map {
////////        DatabaseVideo(
////////            title = it.title,
////////            description = it.description,
////////            url = it.url,
////////            updated = it.updated,
////////            thumbnail = it.thumbnail)
//////    }
////}