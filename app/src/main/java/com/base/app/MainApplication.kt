package com.base.app

import android.app.Activity
import android.app.Application
import com.base.app.di.DaggerProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MainApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerProvider.initialize(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchAndroidInjector
}