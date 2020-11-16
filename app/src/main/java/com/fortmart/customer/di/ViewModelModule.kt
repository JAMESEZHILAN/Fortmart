package com.fortmart.customer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fortmart.customer.ui.dashboard.stores.StoresViewModel
import com.fortmart.customer.ui.dashboard.stores.storeList.StoreListViewModel
import com.fortmart.customer.ui.dashboard.items.CategoryListViewModel
import com.fortmart.customer.ui.dashboard.orders.OrdersViewModel
import com.fortmart.customer.ui.dashboard.settings.ThirdViewModel
import com.fortmart.customer.ui.login.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindOtpViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StoresViewModel::class)
    abstract fun bindFirstViewModel(viewModel: StoresViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrdersViewModel::class)
    abstract fun bindSecondViewModel(viewModel: OrdersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ThirdViewModel::class)
    abstract fun bindThirdViewModel(viewModel: ThirdViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StoreListViewModel::class)
    abstract fun bindDetailViewModel(viewModel: StoreListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryListViewModel::class)
    abstract fun bindActionViewModel(viewModel: CategoryListViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}