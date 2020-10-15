package com.fortmart.customer.ui.dashboard.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fortmart.customer.R
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.ui.dashboard.BaseFragment
import javax.inject.Inject

class SecondFragment : BaseFragment(R.layout.fragment_second) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SecondViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}