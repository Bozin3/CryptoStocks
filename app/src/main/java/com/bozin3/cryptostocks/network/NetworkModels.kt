package com.bozin3.cryptostocks.network

import com.squareup.moshi.Json

data class USD(
    val price: Float,
    @Json(name = "percent_change_1h")
    val percentChangeHour: Float,
    @Json(name = "percent_change_24h")
    val percentChangeDay: Float,
    @Json(name = "percent_change_7d")
    val percentChangeWeek: Float,
    @Json(name = "market_cap")
    val marketCap: Float
)

data class Quote(
    @Json(name = "USD")
    val usd: USD
)

data class CryptoNetworkModel(
    val id: Long,
    val name: String,
    val symbol: String,
    @Json(name = "total_supply")
    val totalSupply: Float,
    @Json(name = "max_supply")
    val maxSupply: Float?,
    val quote: Quote
)
