package com.bozin3.cryptostocks.repository

import androidx.lifecycle.*
import com.bozin3.cryptostocks.localdb.dao.CryptoDao
import com.bozin3.cryptostocks.localdb.entity.Crypto
import com.bozin3.cryptostocks.models.CryptoNetworkModel
import com.bozin3.cryptostocks.network.ApiResponse
import com.bozin3.cryptostocks.network.CryptoApiService
import com.bozin3.cryptostocks.utils.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CryptoStocksRepository @Inject constructor(private val cryptoDao: CryptoDao, private val cryptoService: CryptoApiService) {

    suspend fun syncData(): Result{

        var result = Result.success()

        try {
            val response = downloadDataFromNetwork()

            // if there is no error, we transform the network allData to database model in the default dispatcher
            if(response.status.errorCode == 0){
                val databaseList = transformToDatabaseModel(response.data)
                // saving allData in local db
                insertData(databaseList)
            }else{
                // if error code is not zero, then we have some problem
                result = Result.error(response.status.errorMessage?: "Something wrong happened, please try again")
            }
        }catch (exception: Exception){
            result = Result.error(exception.message?: "Something wrong happened, please try again")
        }

        return result
    }

    fun getAllData(): LiveData<List<Crypto>> {
        return cryptoDao.getAllData()
    }

    fun getCryptoById(id: Long): LiveData<Crypto> {
        return cryptoDao.getCryptoById(id)
    }

    fun filterData(queryText: String): LiveData<List<Crypto>> {
        val queryStr = "%$queryText%"
        return cryptoDao.filterCryptoData(queryStr)
    }

    private suspend fun downloadDataFromNetwork(): ApiResponse {
        return withContext(Dispatchers.IO){
            Timber.d("downloading allData from network :  ${Thread.currentThread().name}")
            cryptoService.getStocksData()
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
            cryptoDao.insertAll(databaseList)
        }
    }
}
