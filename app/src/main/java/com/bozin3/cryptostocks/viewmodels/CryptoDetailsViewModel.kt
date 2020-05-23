package com.bozin3.cryptostocks.viewmodels

import androidx.lifecycle.*
import com.bozin3.cryptostocks.repository.CryptoStocksRepository
import javax.inject.Inject

class CryptoDetailsViewModel @Inject constructor(val cryptoStocksRepo: CryptoStocksRepository) : ViewModel() {

    fun getCryptoData(id: Long) = cryptoStocksRepo.getCryptoById(id)

}
