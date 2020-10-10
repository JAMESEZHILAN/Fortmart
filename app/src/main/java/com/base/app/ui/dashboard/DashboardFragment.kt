package com.base.app.ui.dashboard

import androidx.fragment.app.Fragment
import com.base.app.MainActivity
import com.base.app.enums.RetrofitStatus
import com.base.app.utils.setLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

abstract class DashboardFragment: Fragment() {

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