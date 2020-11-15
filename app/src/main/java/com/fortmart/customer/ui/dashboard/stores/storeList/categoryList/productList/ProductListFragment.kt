package com.fortmart.customer.ui.dashboard.stores.storeList.categoryList.productList

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentProductListBinding
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.model.other.ProductDetails
import com.fortmart.customer.ui.dashboard.BaseFragment
import com.google.android.material.snackbar.Snackbar
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
        setUpKart()
    }
    private fun setUpKart(){
        val kartSnackBar = Snackbar.make(
            binding?.root!!,
            "Added 4 items",
            Snackbar.LENGTH_INDEFINITE
        )
        kartSnackBar.setAction("${getString(R.string.currency)}123") {

        }
        val kartView = kartSnackBar.view
        val snackBarTextView = kartView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        val snackBarAction = kartView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        snackBarTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 28F)
        snackBarTextView.setTypeface(snackBarTextView.typeface, Typeface.BOLD)
        snackBarAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, 28F)
        snackBarAction.setTypeface(snackBarTextView.typeface, Typeface.BOLD)
        snackBarAction.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorTextWhite
            )
        )
        snackBarAction.setCompoundDrawablesWithIntrinsicBounds(
            null, null, ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_kart_link
            ), null
        )
        snackBarAction.compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen.size_08)
        kartView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )
        kartView.setOnClickListener {
            kartSnackBar.dismiss()
        }
        kartSnackBar.show()
    }

}