package com.base.app.ui.dashboard.stores.storeList

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.app.R
import com.base.app.databinding.FragmentStoreListBinding
import com.base.app.di.ViewModelFactory
import com.base.app.model.other.StoreDetails
import com.base.app.ui.dashboard.BaseFragment
import javax.inject.Inject

class StoreListFragment : BaseFragment(R.layout.fragment_store_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: StoreListViewModel by viewModels {
        viewModelFactory
    }

    private var binding: FragmentStoreListBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStoreListBinding.bind(view)
        binding?.storeListRV?.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = StoreListRVAdapter(arrayOf(
                StoreDetails("Fortmart Online store",R.drawable.ic_shoe_shop, "Grocery", "Delivery", "25kms", "4.5","Open","20% offer")
            ))
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.search, menu)
    }

}