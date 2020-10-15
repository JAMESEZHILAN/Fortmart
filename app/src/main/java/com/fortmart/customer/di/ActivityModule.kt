package com.fortmart.customer.di

import com.fortmart.customer.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun contributeLoginActivity(): MainActivity
}