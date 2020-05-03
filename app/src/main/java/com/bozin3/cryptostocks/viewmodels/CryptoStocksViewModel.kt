package com.bozin3.cryptostocks.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.bozin3.cryptostocks.localdb.AppDatabase
import com.bozin3.cryptostocks.localdb.entity.Crypto
import com.bozin3.cryptostocks.repository.CryptoStocksRepository
import kotlinx.coroutines.*
import timber.log.Timber

class CryptoStocksViewModel(app: Application) : AndroidViewModel(app) {

    private val cryptoStocksRepository = CryptoStocksRepository(AppDatabase.getInstance(app))

    private val _loadingStatus = MutableLiveData<Boolean>()

    val loadingStatus: LiveData<Boolean>
        get() = _loadingStatus

    val error: LiveData<String> =  cryptoStocksRepository.error

    private val queryText: MutableLiveData<String> = MutableLiveData()

    val cryptoData: LiveData<List<Crypto>> = queryText.switchMap {query ->
        if(query.isNullOrEmpty()) {
            cryptoStocksRepository.allData
        } else {
            cryptoStocksRepository.filterData(query)
        }
    }

    init {
        // on start we are retrieving all allData from local db
        _loadingStatus.value = true
        // get all data on start
        this.filterData("")

        viewModelScope.launch {
            Timber.d("before syncData() : ${Thread.currentThread().name}")
            cryptoStocksRepository.syncData()
        }

    }

    fun filterData(query: String){
        queryText.value = query
    }

    fun doneLoading(){
        _loadingStatus.value = false
    }
}
