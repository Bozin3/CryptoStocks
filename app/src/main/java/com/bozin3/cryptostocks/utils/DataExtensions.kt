package com.bozin3.cryptostocks.utils

import com.bozin3.cryptostocks.localdb.entity.Crypto
import com.bozin3.cryptostocks.models.CryptoNetworkModel

fun List<CryptoNetworkModel>.asDatabaseModel(): List<Crypto> {
    return this.map {
        Crypto(
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
