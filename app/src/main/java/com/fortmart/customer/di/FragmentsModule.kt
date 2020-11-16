package com.fortmart.customer.di

import com.fortmart.customer.ui.dashboard.stores.StoresFragment
import com.fortmart.customer.ui.dashboard.stores.storeList.StoreListFragment
import com.fortmart.customer.ui.dashboard.items.CategoryListFragment
import com.fortmart.customer.ui.dashboard.orders.OrdersFragment
import com.fortmart.customer.ui.dashboard.items.productList.ProductListFragment
import com.fortmart.customer.ui.dashboard.third.ThirdFragment
import com.fortmart.customer.ui.login.login.LoginFragment
import com.fortmart.customer.ui.login.language.LanguageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeLanguageFragment(): LanguageFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeStoresFragment(): StoresFragment

    @ContributesAndroidInjector
    abstract fun contributeSecondFragment(): OrdersFragment

    @ContributesAndroidInjector
    abstract fun contributeThirdFragment(): ThirdFragment

    @ContributesAndroidInjector
    abstract fun contributeStoreListFragment(): StoreListFragment

    @ContributesAndroidInjector
    abstract fun contributeCategoryListFragment(): CategoryListFragment

    @ContributesAndroidInjector
    abstract fun contributeProductListFragment(): ProductListFragment
}