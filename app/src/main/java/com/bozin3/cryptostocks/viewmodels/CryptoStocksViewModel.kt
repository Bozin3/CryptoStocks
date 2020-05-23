package com.bozin3.cryptostocks.viewmodels

import androidx.lifecycle.*
import com.bozin3.cryptostocks.localdb.entity.Crypto
import com.bozin3.cryptostocks.repository.CryptoStocksRepository
import com.bozin3.cryptostocks.repository.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoStocksViewModel @Inject constructor(val cryptoStocksRepo: CryptoStocksRepository) : ViewModel() {

    private val queryText: MutableLiveData<String> = MutableLiveData()
    val cryptoData: LiveData<List<Crypto>> = queryText.switchMap {query ->
        if(query.isNullOrEmpty()) {
            cryptoStocksRepo.getAllData()
        } else {
            cryptoStocksRepo.filterData(query)
        }
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        // get all data on start
        this.filterData("")

        viewModelScope.launch {
            setLoading(true)
            val result = cryptoStocksRepo.syncData()
            setLoading(false)

            if(result.status == Result.Status.ERROR) {
                result.message?.let {
                    setError(it)
                }
            }
        }
    }

    fun filterData(query: String) {
        queryText.value = query
    }

    fun setError(message: String) {
        _error.value = message
    }

    fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    override fun onCleared() {
        super.onCleared()
    }
}
