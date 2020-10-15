package com.fortmart.customer.ui.dashboard.stores.storeList

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentStoreListBinding
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.model.other.StoreDetails
import com.fortmart.customer.ui.dashboard.BaseFragment
import javax.inject.Inject

class StoreListFragment : BaseFragment(R.layout.fragment_store_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: StoreListViewModel by viewModels {
        viewModelFactory
    }

    private var binding: FragmentStoreListBinding? = null
    private var rvAdapter: StoreListRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStoreListBinding.bind(view)
        rvAdapter = StoreListRVAdapter(arrayOf(
            StoreDetails("Fortmart Online store",R.drawable.ic_shoe_shop, "Grocery", "Delivery", "25kms", "4.5","Open","20% offer")
        ))
        rvAdapter?.itemClickListener = { pos, v ->
            findNavController().navigate(StoreListFragmentDirections.actionToCategoryList())
        }
        binding?.storeListRV?.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter =rvAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.search, menu)
    }

}