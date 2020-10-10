package com.base.app.di

import com.base.app.ui.dashboard.first.FirstFragment
import com.base.app.ui.dashboard.first.details.DetailFragment
import com.base.app.ui.dashboard.first.details.action.ActionFragment
import com.base.app.ui.dashboard.fourth.FourthFragment
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
    abstract fun contributeFirstFragment(): FirstFragment

    @ContributesAndroidInjector
    abstract fun contributeSecondFragment(): SecondFragment

    @ContributesAndroidInjector
    abstract fun contributeThirdFragment(): ThirdFragment

    @ContributesAndroidInjector
    abstract fun contributeFourthFragment(): FourthFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributeActionFragment(): ActionFragment
}