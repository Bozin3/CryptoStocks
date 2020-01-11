package com.bozin3.cryptostocks.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StocksViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoStocksViewModel::class.java)) {
            return CryptoStocksViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}

class DetailsViewModelFactory(val coinId: Long, val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoDetailsViewModel::class.java)) {
            return CryptoDetailsViewModel(coinId, app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}