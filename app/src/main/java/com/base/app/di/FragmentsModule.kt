package com.base.app.di

import com.base.app.ui.dashboard.stores.StoresFragment
import com.base.app.ui.dashboard.stores.storeList.StoreListFragment
import com.base.app.ui.dashboard.stores.storeList.action.ActionFragment
import com.base.app.ui.dashboard.second.SecondFragment
import com.base.app.ui.dashboard.third.ThirdFragment
import com.base.app.ui.login.login.LoginFragment
import com.base.app.ui.login.language.LanguageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeUsernameFragment(): LanguageFragment

    @ContributesAndroidInjector
    abstract fun contributeOtpFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeFirstFragment(): StoresFragment

    @ContributesAndroidInjector
    abstract fun contributeSecondFragment(): SecondFragment

    @ContributesAndroidInjector
    abstract fun contributeThirdFragment(): ThirdFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): StoreListFragment

    @ContributesAndroidInjector
    abstract fun contributeActionFragment(): ActionFragment
}