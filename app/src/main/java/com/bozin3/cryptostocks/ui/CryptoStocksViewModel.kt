package com.bozin3.cryptostocks.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bozin3.cryptostocks.network.CryptoApi
import com.bozin3.cryptostocks.models.CryptoDomainModel
import com.bozin3.cryptostocks.network.CryptoNetworkModel
import com.bozin3.cryptostocks.network.Response
import com.bozin3.cryptostocks.utils.asDomainModel
import kotlinx.coroutines.*
import java.lang.Exception

class CryptoStocksViewModel : ViewModel() {

    private var job = Job()

    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val _cryptoData = MutableLiveData<List<CryptoDomainModel>>()

    val cryptoData: LiveData<List<CryptoDomainModel>>
        get() = _cryptoData

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    init {
        // on start we are retrieving data
        getCryptoStocksData()
        //_cryptoData.value = arrayListOf(CryptoDomainModel(1,"BTC","Bitcoin",0f,0f,0f,0f))
    }

    fun getCryptoStocksData(){

        coroutineScope.launch {
            try {
                // network request on IO dispatcher
                val response = downloadDataFromNetwork()

                // if error code is not zero, then we have some problem
                if(response.status.errorCode != 0){

                    _error.value = response.status.errorMessage?: "Unknown error!"

                }else{
                    // if there is no error, then we transfor the network data to domain model in the default dispatcher
                    _cryptoData.value = transformDataAsDomain(response.data)
                }
            }catch (error: Exception){
                _error.value = error.message
            }
        }
    }

    suspend fun downloadDataFromNetwork(): Response {
        return withContext(Dispatchers.IO){
            Log.i("DEBUG","NetworkRequest: ${Thread.currentThread().name}")
            CryptoApi.service.getStocksData()
        }
    }

    suspend fun transformDataAsDomain(networkData : List<CryptoNetworkModel>): List<CryptoDomainModel> {
        return withContext(Dispatchers.Default){
            Log.i("DEBUG","Transformations: ${Thread.currentThread().name}")
            networkData.asDomainModel()
        }
    }

    override fun onCleared() {
        super.onCleared()

        // stop network request if we switch to details fragment or close the app
        job.cancel()
    }
}
