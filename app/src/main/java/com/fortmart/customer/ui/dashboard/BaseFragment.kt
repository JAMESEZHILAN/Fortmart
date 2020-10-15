package com.fortmart.customer.ui.dashboard

import androidx.fragment.app.Fragment
import com.fortmart.customer.MainActivity
import com.fortmart.customer.enums.RetrofitStatus
import com.fortmart.customer.utils.setLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

abstract class BaseFragment(id:Int): Fragment(id) {

    private val parentJob = Job()
    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    override fun onStart() {
        super.onStart()
        (activity as MainActivity?)?.executePendingTransitions()
    }

    override fun onPause() {
        super.onPause()
        setLoader(RetrofitStatus.COMPLETED)
        parentJob.cancelChildren()
    }
}