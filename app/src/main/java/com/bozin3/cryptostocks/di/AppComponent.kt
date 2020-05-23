package com.bozin3.cryptostocks.di

import android.app.Application
import com.bozin3.cryptostocks.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        FragmentBuildersModule::class,
        NetworkModule::class,
        RoomModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

}