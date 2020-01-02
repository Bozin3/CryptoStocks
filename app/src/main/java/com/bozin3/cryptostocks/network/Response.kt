package com.bozin3.cryptostocks.network

import com.squareup.moshi.Json

data class Status(
    @Json(name = "error_code")
    val errorCode: Int,
    @Json(name = "error_message")
    val errorMessage: String?
)

data class Response(
    val status: Status,
    val data: List<CryptoNetworkModel>
)

