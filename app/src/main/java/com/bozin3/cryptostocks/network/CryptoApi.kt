package com.bozin3.cryptostocks.network

import com.bozin3.cryptostocks.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers

interface CryptoApiService {
    @Headers("X-CMC_PRO_API_KEY: $API_KEY")
    @GET("v1/cryptocurrency/listings/latest?start=1&limit=100&convert=USD")
    suspend fun getStocksData(): ApiResponse
}
