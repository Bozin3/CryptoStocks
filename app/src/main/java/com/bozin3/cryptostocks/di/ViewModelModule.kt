package com.bozin3.cryptostocks.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bozin3.cryptostocks.viewmodels.CryptoDetailsViewModel
import com.bozin3.cryptostocks.viewmodels.CryptoStocksViewModel
import com.bozin3.cryptostocks.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CryptoDetailsViewModel::class)
    abstract fun bindCryptoDetailsViewModel(viewModel: CryptoDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CryptoStocksViewModel::class)
    abstract fun bindCryptoStocksViewModel(viewModel: CryptoStocksViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
