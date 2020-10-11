package com.base.app.ui.dashboard.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.base.app.R
import com.base.app.di.ViewModelFactory
import com.base.app.ui.dashboard.BaseFragment
import javax.inject.Inject

class ThirdFragment : BaseFragment(R.layout.fragment_third) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ThirdViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}