package com.bozin3.cryptostocks.di

import com.bozin3.cryptostocks.ui.CryptoDetailsFragment
import com.bozin3.cryptostocks.ui.CryptoStocksFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCryptoStocksFragment(): CryptoStocksFragment

    @ContributesAndroidInjector
    abstract fun contributeCryptoDetailsFragment(): CryptoDetailsFragment
}