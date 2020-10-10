package com.base.app.di

import com.base.app.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun contributeLoginActivity(): MainActivity
}