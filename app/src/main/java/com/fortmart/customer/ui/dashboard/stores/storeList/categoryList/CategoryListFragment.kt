package com.fortmart.customer.ui.dashboard.stores.storeList.categoryList

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentCategoryListBinding
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.model.other.ImageMenu
import com.fortmart.customer.ui.dashboard.BaseFragment
import javax.inject.Inject

class CategoryListFragment : BaseFragment(R.layout.fragment_category_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CategoryListViewModel by viewModels {
        viewModelFactory
    }

    private var binding: FragmentCategoryListBinding? = null
    private var rvAdapter: CategoryRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryListBinding.bind(view)
        rvAdapter = CategoryRVAdapter(
            arrayOf(
                ImageMenu("Fruits & vegitebles", R.drawable.fruits)
            )
        )
        rvAdapter?.itemClickListener = { pos, v ->
            findNavController().navigate(CategoryListFragmentDirections.actionToProductList())
        }
        binding?.dashboardMenuRV?.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = rvAdapter!!
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_address, menu)
    }

}