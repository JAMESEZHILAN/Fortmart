package com.base.app.ui.dashboard.stores

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.base.app.R
import com.base.app.databinding.FragmentStoresBinding
import com.base.app.di.ViewModelFactory
import com.base.app.model.other.DashboardMenu
import com.base.app.ui.dashboard.BaseFragment
import javax.inject.Inject

class StoresFragment : BaseFragment(R.layout.fragment_stores) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: StoresViewModel by viewModels {
        viewModelFactory
    }

    private var binding: FragmentStoresBinding? = null
    private var rvAdapter: StoresMenuRVAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStoresBinding.bind(view)
        rvAdapter = StoresMenuRVAdapter(arrayOf(
            DashboardMenu("10 stores near you",R.drawable.ic_shoe_shop),
            DashboardMenu("Basket",R.drawable.ic_shopping_cart),
            DashboardMenu("Store register",R.drawable.ic_guarantee)))
        rvAdapter?.itemClickListener = { pos, v ->
            findNavController().navigate(StoresFragmentDirections.actionToDetails())
        }
        binding?.dashboardMenuRV?.apply{
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = rvAdapter!!
        }
    }

}