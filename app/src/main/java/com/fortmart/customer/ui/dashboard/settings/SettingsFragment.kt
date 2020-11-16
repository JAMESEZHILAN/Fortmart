package com.fortmart.customer.ui.dashboard.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentOrdersBinding
import com.fortmart.customer.databinding.FragmentSettingsBinding
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.ui.dashboard.BaseFragment
import com.fortmart.customer.ui.dashboard.items.CategoryListViewModel
import com.fortmart.customer.ui.dashboard.orders.OrdersRVAdapter
import javax.inject.Inject

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CategoryListViewModel by viewModels {
        viewModelFactory
    }

    private var binding: FragmentSettingsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        binding?.clickListener = View.OnClickListener {

        }
    }

}