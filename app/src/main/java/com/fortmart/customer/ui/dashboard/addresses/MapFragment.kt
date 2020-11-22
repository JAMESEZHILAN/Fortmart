package com.fortmart.customer.ui.dashboard.addresses

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentMapBinding
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.ui.dashboard.BaseFragment
import com.fortmart.customer.ui.dashboard.items.CategoryListViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

class MapFragment : BaseFragment(R.layout.fragment_map) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CategoryListViewModel by viewModels {
        viewModelFactory
    }

    private var binding: FragmentMapBinding? = null
    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mapFragment.getMapAsync { map -> loadMap(map) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)
    }

    private fun loadMap(googleMap: GoogleMap) {
        map = googleMap
        if (map != null) {
            Toast.makeText(requireContext(), "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Error - Map was null!!", Toast.LENGTH_SHORT).show()
        }
    }

}