package com.bozin3.cryptostocks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bozin3.cryptostocks.localdb.AppDatabase
import com.bozin3.cryptostocks.localdb.entity.CryptoDatabaseModel
import com.bozin3.cryptostocks.repository.CryptoStocksRepository

class CryptoDetailsViewModel(coinId: Long, val app: Application) : AndroidViewModel(app) {

    private val cryptoStocksRepository = CryptoStocksRepository(AppDatabase.getInstance(app))

    private val _selectedCryptoId = MutableLiveData<Long>()

    val selectedCrypto: LiveData<CryptoDatabaseModel> = Transformations.switchMap(_selectedCryptoId){id ->
        cryptoStocksRepository.getCryptoById(id)
    }

    init {
        _selectedCryptoId.value = coinId
    }

}
