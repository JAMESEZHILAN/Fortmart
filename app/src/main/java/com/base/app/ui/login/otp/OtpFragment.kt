package com.base.app.ui.login.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.base.app.MainActivity
import com.base.app.R
import com.base.app.databinding.FragmentOtpBinding
import com.base.app.di.Injectable
import com.base.app.di.ViewModelFactory
import com.base.app.model.entity.UserDetails
import com.base.app.model.response.OtpStatus
import com.base.app.shared.Constants.LOGGED_IN
import com.base.app.shared.Constants.USER_CREDENTIALS
import com.base.app.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class OtpFragment : Fragment(), Injectable {

    private val parentJob = Job()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val args: OtpFragmentArgs by navArgs()
    private val viewModel: OtpViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentOtpBinding

    private val onSubmitListener = View.OnClickListener {
        val otp = "" + binding.otp1.text + binding.otp2.text + binding.otp3.text + binding.otp4.text
//        if (otp.isNotEmpty() && otp.length == 4) {
//            coroutineScope.launch(Dispatchers.IO) {
//                viewModel.verifyOtp(otpWrapper)
//            }
//        } else {
//            showAlert(requireContext(), getString(R.string.error_empty_otp))
//        }
        loginToDashboard()
    }

    private val onResendListener = View.OnClickListener {
//        coroutineScope.launch(Dispatchers.IO) {
//            viewModel.resendOtp(args.userDetails.phone!!)
//        }
    }

    private val otpSuccessObserver = Observer<EventWrapper<UserDetails?>> {
        it.nullifyIfHandled()?.let {
//            navigateToPasswordCreation()
            loginToDashboard()
        }
    }

    private val resendSuccessObserver = Observer<EventWrapper<OtpStatus?>> {
        it.nullifyIfHandled()?.let { otpStatus ->
            Toast.makeText(context, otpStatus.message, Toast.LENGTH_LONG).show()
            launchResendOTPCountdown()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        binding.submitClicklistener = onSubmitListener
        binding.resendClicklistener = onResendListener
        addOtpListeners(binding.otp1, binding.otp2, binding.otp3, binding.otp4)
        launchResendOTPCountdown()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addObservers()
    }

    private fun addOtpListeners(otp1: EditText, otp2: EditText, otp3: EditText, otp4: EditText) {
        binding.otp1.setOnEditorActionListener(FieldSubmitListener { binding.submit.callOnClick() })
        binding.otp2.setOnEditorActionListener(FieldSubmitListener { binding.submit.callOnClick() })
        binding.otp3.setOnEditorActionListener(FieldSubmitListener { binding.submit.callOnClick() })
        binding.otp4.setOnEditorActionListener(FieldSubmitListener { binding.submit.callOnClick() })
        otp1.addTextChangedListener(OtpTextChangeListeners(otp1))
        otp2.addTextChangedListener(OtpTextChangeListeners(otp2))
        otp3.addTextChangedListener(OtpTextChangeListeners(otp3))
        otp4.addTextChangedListener(OtpTextChangeListeners(otp4))
        otp1.setOnKeyListener(otpKeyListener)
        otp2.setOnKeyListener(otpKeyListener)
        otp3.setOnKeyListener(otpKeyListener)
        otp4.setOnKeyListener(otpKeyListener)
    }

    private fun addObservers() {
        viewModel.apply {
            getOtpSuccess().observe(viewLifecycleOwner, otpSuccessObserver)
            getOtpFailure().observe(viewLifecycleOwner, errorAlertObserver(requireContext()))
            getResendOtpSuccess().observe(viewLifecycleOwner, resendSuccessObserver)
            getResendOtpFailure().observe(viewLifecycleOwner, errorAlertObserver(requireContext()))
            getRetrofitStatus().observe(viewLifecycleOwner, Observer { setLoader(it) })
        }
    }

//    private fun navigateToPasswordCreation() {
//        findNavController().navigate(OtpFragmentDirections.actionCreatePassword(args.userDetails))
//    }

    private fun launchResendOTPCountdown() {
        binding.resendCode.text = getString(R.string.label_resend_otp_disabled)
        object : CountDownTimer(59000, 1000) {
            override fun onFinish() {
                if (this@OtpFragment.isVisible && !this@OtpFragment.isRemoving) {
                    binding.countDown.text = ""
                    binding.resendCode.text = getString(R.string.label_resend_otp_enabled)
                    binding.resendCode.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorAccent
                        )
                    )
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.countDown.text = timerConverter(millisUntilFinished)
            }
        }.start()
    }

    private fun loginToDashboard(){
        requireContext().getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE).edit()
            .putBoolean(LOGGED_IN, true).apply()
        activity?.startActivity(Intent.makeRestartActivityTask(activity?.intent?.component))
    }
}
