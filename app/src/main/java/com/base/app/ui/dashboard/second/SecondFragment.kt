package com.base.app.ui.dashboard.second

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.base.app.R
import com.base.app.data.repository.DashboardRepository
import com.base.app.di.ViewModelFactory
import com.base.app.ui.dashboard.DashboardFragment
import com.base.app.ui.dashboard.first.FirstViewModel
import javax.inject.Inject

class SecondFragment : DashboardFragment() {

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