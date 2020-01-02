package com.bozin3.cryptostocks.network

import com.bozin3.cryptostocks.utils.API_KEY
import com.bozin3.cryptostocks.utils.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CryptoApiService {

    @Headers("X-CMC_PRO_API_KEY: $API_KEY")
    @GET("v1/cryptocurrency/listings/latest?start=1&limit=100&convert=USD")
    suspend fun getStocksData(): Response

}

object CryptoApi {
    val service : CryptoApiService by lazy {
        retrofit.create(CryptoApiService::class.java) }
}
