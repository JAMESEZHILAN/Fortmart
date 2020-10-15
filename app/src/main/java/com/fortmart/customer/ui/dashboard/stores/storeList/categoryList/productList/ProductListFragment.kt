package com.fortmart.customer.ui.dashboard.stores.storeList.categoryList.productList

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentProductListBinding
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.model.other.ProductDetails
import com.fortmart.customer.ui.dashboard.BaseFragment
import javax.inject.Inject

class ProductListFragment : BaseFragment(R.layout.fragment_product_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ProductListViewModel by viewModels {
        viewModelFactory
    }

    private var binding: FragmentProductListBinding? = null
    private var rvAdapter: ProductListRVAdapter? = null
    private var subcategoryvAdapter: SubCategoryListRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductListBinding.bind(view)
        rvAdapter = ProductListRVAdapter(
            arrayOf(
                ProductDetails("Udhayam toor dal", "200", "180", R.drawable.ic_apple)
            )
        )
        rvAdapter?.itemClickListener = { pos, v ->

        }
        binding?.productListRV?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter!!
        }
        subcategoryvAdapter = SubCategoryListRVAdapter(
            arrayOf(
                "All","Dairy","Stationary","Milk products","Frozen","Ready mix","Instant","Snacks","Beverages"
            )
        )
        subcategoryvAdapter?.itemClickListener = { pos, v ->

        }
        binding?.subCategoryListRV?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            adapter = subcategoryvAdapter!!
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.search, menu)
    }

}