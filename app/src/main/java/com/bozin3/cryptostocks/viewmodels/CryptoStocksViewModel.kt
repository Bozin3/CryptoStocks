package com.bozin3.cryptostocks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bozin3.cryptostocks.localdb.AppDatabase
import com.bozin3.cryptostocks.network.CryptoApi
import com.bozin3.cryptostocks.models.CryptoDomainModel
import com.bozin3.cryptostocks.models.CryptoNetworkModel
import com.bozin3.cryptostocks.network.ApiResponse
import com.bozin3.cryptostocks.repository.CryptoStocksRepository
import com.bozin3.cryptostocks.utils.asDomainModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception

class CryptoStocksViewModel(app: Application) : AndroidViewModel(app) {

    private val cryptoStocksRepository = CryptoStocksRepository(AppDatabase.getInstance(app))

    private var job = Job()

    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    val cryptoData: LiveData<List<CryptoDomainModel>>
        get() = cryptoStocksRepository.data

    val error: LiveData<String>
        get() = cryptoStocksRepository.error

    init {
        // on start we are retrieving data
        getCryptoStocksData()

        //test data
        //_cryptoData.value = arrayListOf(CryptoDomainModel(1,"BTC","Bitcoin",0f,0f,0f,0f))
    }

    fun getCryptoStocksData(){

        coroutineScope.launch {
            Timber.d("before getCryptoData() : ${Thread.currentThread().name}")
            cryptoStocksRepository.getCryptoData()
        }

    }

    override fun onCleared() {
        super.onCleared()

        // stop network request if we switch to details fragment or close the app
        job.cancel()
    }
}
