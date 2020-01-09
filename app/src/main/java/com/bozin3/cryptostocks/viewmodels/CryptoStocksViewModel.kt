package com.bozin3.cryptostocks.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.bozin3.cryptostocks.localdb.AppDatabase
import com.bozin3.cryptostocks.localdb.entity.CryptoDatabaseModel
import com.bozin3.cryptostocks.models.CryptoDomainModel
import com.bozin3.cryptostocks.repository.CryptoStocksRepository
import com.bozin3.cryptostocks.utils.toDomainModel
import kotlinx.coroutines.*
import timber.log.Timber

class CryptoStocksViewModel(app: Application) : AndroidViewModel(app) {

    private val cryptoStocksRepository = CryptoStocksRepository(AppDatabase.getInstance(app))

    private val _allData: LiveData<List<CryptoDatabaseModel>> = cryptoStocksRepository.allData

    val allData = _allData.switchMap {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            Timber.d("transforming db allData to domain model : ${Thread.currentThread().name}")
            emit(it.toDomainModel())
        }
    }

    private val queryText: MutableLiveData<String> = MutableLiveData()

    val filteredData: LiveData<List<CryptoDomainModel>> = queryText.switchMap {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            Timber.d("transforming db filteredData to domain model : ${Thread.currentThread().name}")
            emit(cryptoStocksRepository.filterData(it).toDomainModel())
        }
    }

    val error: LiveData<String> =  cryptoStocksRepository.error

    init {
        // on start we are retrieving all allData from local db
        viewModelScope.launch {
            Timber.d("before refreshData() : ${Thread.currentThread().name}")
            cryptoStocksRepository.refreshData()
        }

    }

    fun filterData(query: String){
        queryText.value = query
    }
}
