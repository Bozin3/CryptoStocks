package com.bozin3.cryptostocks.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bozin3.cryptostocks.localdb.AppDatabase
import com.bozin3.cryptostocks.localdb.entity.CryptoDatabaseModel
import com.bozin3.cryptostocks.models.CryptoDomainModel
import com.bozin3.cryptostocks.models.CryptoNetworkModel
import com.bozin3.cryptostocks.network.ApiResponse
import com.bozin3.cryptostocks.network.CryptoApi
import com.bozin3.cryptostocks.utils.asDatabaseModel
import com.bozin3.cryptostocks.utils.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class CryptoStocksRepository(private val database: AppDatabase) {

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    suspend fun getCryptoData() {
        try {
            val response = downloadDataFromNetwork()

            // if there is no error, we transform the network data to database model in the default dispatcher
            if(response.status.errorCode == 0){

                val databaseList = transformToDatabaseModel(response.data)

                // saving data in local db
                insertData(databaseList)

            }else{

                // if error code is not zero, then we have some problem
                _error.value = response.status.errorMessage?: "Unknown error!"
            }

        }catch (exception: Exception){
            _error.value = exception.message
        }
    }

    val data: LiveData<List<CryptoDomainModel>> = Transformations.map(database.getCryptoDao().getAllData()) {
        Timber.d("transforming db data to domain model : ${Thread.currentThread().name}")
        it.toDomainModel()
    }

    private suspend fun downloadDataFromNetwork(): ApiResponse {
        return withContext(Dispatchers.IO){
            Timber.d("downloading data from network :  ${Thread.currentThread().name}")
            CryptoApi.service.getStocksData()
        }
    }

    private suspend fun transformToDatabaseModel(networkData : List<CryptoNetworkModel>): List<CryptoDatabaseModel> {
        return withContext(Dispatchers.Default){
            Timber.d("transforming data to db model : ${Thread.currentThread().name}")
            networkData.asDatabaseModel()
        }
    }

    private suspend fun insertData(databaseList: List<CryptoDatabaseModel>){
        return withContext(Dispatchers.IO){
            Timber.d("inserting data : ${Thread.currentThread().name}")
            database.getCryptoDao().insertAll(databaseList)
        }
    }
}
