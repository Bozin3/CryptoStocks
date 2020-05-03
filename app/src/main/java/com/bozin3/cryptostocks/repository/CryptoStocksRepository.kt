package com.bozin3.cryptostocks.repository

import androidx.lifecycle.*
import com.bozin3.cryptostocks.localdb.AppDatabase
import com.bozin3.cryptostocks.localdb.entity.Crypto
import com.bozin3.cryptostocks.models.CryptoNetworkModel
import com.bozin3.cryptostocks.network.ApiResponse
import com.bozin3.cryptostocks.network.CryptoApi
import com.bozin3.cryptostocks.utils.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class CryptoStocksRepository(private val database: AppDatabase) {

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    val allData: LiveData<List<Crypto>> =  database.getCryptoDao().getAllData()

    suspend fun syncData() {
        try {
            val response = downloadDataFromNetwork()

            // if there is no error, we transform the network allData to database model in the default dispatcher
            if(response.status.errorCode == 0){

                val databaseList = transformToDatabaseModel(response.data)

                // saving allData in local db
                insertData(databaseList)

            }else{

                // if error code is not zero, then we have some problem
                _error.value = response.status.errorMessage?: "Unknown error!"
            }

        }catch (exception: Exception){
            _error.value = exception.message
        }
    }

    fun getCryptoById(id: Long): LiveData<Crypto> {
        return database.getCryptoDao().getCryptoById(id)
    }

    fun filterData(queryText: String): LiveData<List<Crypto>> {
        val queryStr = "%$queryText%"
        return database.getCryptoDao().filterCryptoData(queryStr)
    }

    private suspend fun downloadDataFromNetwork(): ApiResponse {
        return withContext(Dispatchers.IO){
            Timber.d("downloading allData from network :  ${Thread.currentThread().name}")
            CryptoApi.service.getStocksData()
        }
    }

    private suspend fun transformToDatabaseModel(networkData : List<CryptoNetworkModel>): List<Crypto> {
        return withContext(Dispatchers.Default){
            Timber.d("transforming allData to db model : ${Thread.currentThread().name}")
            networkData.asDatabaseModel()
        }
    }

    private suspend fun insertData(databaseList: List<Crypto>){
        return withContext(Dispatchers.IO){
            Timber.d("inserting allData : ${Thread.currentThread().name}")
            database.getCryptoDao().insertAll(databaseList)
        }
    }
}
