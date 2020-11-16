package com.fortmart.customer.ui.dashboard.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentOrdersBinding
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.model.other.OrderDetails
import com.fortmart.customer.ui.dashboard.BaseFragment
import com.fortmart.customer.ui.dashboard.items.CategoryListViewModel
import javax.inject.Inject

class OrdersFragment : BaseFragment(R.layout.fragment_orders) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CategoryListViewModel by viewModels {
        viewModelFactory
    }

    private var binding: FragmentOrdersBinding? = null
    private var rvAdapter: OrdersRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdersBinding.bind(view)
        rvAdapter = OrdersRVAdapter(
            arrayOf(
                OrderDetails("REF-12409745809723", "Velachery", "${getString(R.string.currency)}387 - Paid", "Completed", "September 7, 7:30 PM"),
                OrderDetails("REF-12409745809723", "Guindy", "${getString(R.string.currency)}345 - Unpaid", "Pending", "September 7, 7:30 PM"),
                OrderDetails("REF-12409745809723", "Tambaram", "${getString(R.string.currency)}536 - Unpaid", "Pending", "September 7, 7:30 PM"),
                OrderDetails("REF-12409745809723", "Anna nagar", "${getString(R.string.currency)}145 - Unpaid", "Pending", "September 7, 7:30 PM")
            )
        )
        binding?.ordersListRV?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter!!
        }
    }

}