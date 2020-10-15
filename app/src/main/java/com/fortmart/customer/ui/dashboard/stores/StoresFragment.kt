package com.fortmart.customer.ui.dashboard.stores

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentStoresBinding
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.model.other.ImageMenu
import com.fortmart.customer.ui.dashboard.BaseFragment
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
            ImageMenu("10 stores near you",R.drawable.ic_shoe_shop),
            ImageMenu("Basket",R.drawable.ic_shopping_cart),
            ImageMenu("Store register",R.drawable.ic_guarantee)))
        rvAdapter?.itemClickListener = { pos, v ->
            findNavController().navigate(StoresFragmentDirections.actionToStoreList())
        }
        binding?.dashboardMenuRV?.apply{
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = rvAdapter!!
        }
    }

}