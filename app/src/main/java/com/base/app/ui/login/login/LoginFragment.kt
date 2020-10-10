package com.base.app.ui.login.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.base.app.MainActivity
import com.base.app.R
import com.base.app.databinding.FragmentLoginBinding
import com.base.app.di.Injectable
import com.base.app.model.entity.UserDetails
import com.base.app.model.response.LoginDetails
import com.base.app.shared.Constants.phoneNumberPattern
import com.base.app.utils.EventWrapper
import com.base.app.utils.FieldSubmitListener
import com.base.app.utils.errorAlertObserver
import com.base.app.utils.setLoader
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class LoginFragment : Fragment(), Injectable {

    private val parentJob = Job()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userDetails: UserDetails

    private val onSubmitListener = View.OnClickListener {
        val username = editTextUsername.text.toString()
        if (!username.isBlank()) {
            when {
                username.matches(phoneNumberPattern) -> {
//                    coroutineScope.launch(Dispatchers.IO) {
//                        viewModel.loginWith(email = username)
//                    }
                }
//                username.matches(emailPattern) -> {
//                    coroutineScope.launch(Dispatchers.IO) {
//                        viewModel.loginWith(phone = username.toLong())
//                    }
//                }
                else -> {
                    binding.error.text = getString(R.string.error_invalid_username)
                }
            }
        } else {
            binding.error.text = getString(R.string.error_username_space)
        }
        findNavController().navigate(LoginFragmentDirections.actionVerifyOtp(UserDetails()))
    }

    private val onLoginSuccessObserver = Observer<EventWrapper<LoginDetails?>> {
        it.nullifyIfHandled()?.let {
            findNavController().navigate(LoginFragmentDirections.actionVerifyOtp(UserDetails()))
        }
    }

//    private val onOtpSuccessObserver = Observer<EventWrapper<OtpStatus?>> {
//        it.nullifyIfHandled()?.let { otpStatus ->
//            Toast.makeText(context, "OTP sent successfully", Toast.LENGTH_LONG).show()
//            findNavController().navigate(LoginFragmentDirections.actionVerifyOtp(userDetails))
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.submitOnClickListener = onSubmitListener
        binding.editTextUsername.addTextChangedListener {
            binding.error.text = ""
        }
        binding.editTextUsername.setOnEditorActionListener(FieldSubmitListener { binding.submit.callOnClick() })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        viewModel.apply {
            getLoginSuccess().observe(viewLifecycleOwner, onLoginSuccessObserver)
            getLoginFailure().observe(viewLifecycleOwner, errorAlertObserver(requireContext()))
//            getOtpSuccess().observe(viewLifecycleOwner, onOtpSuccessObserver)
//            getOtpFailure().observe(viewLifecycleOwner, errorAlertObserver(requireContext()))
            getRetrofitStatus().observe(viewLifecycleOwner, Observer { setLoader(it) })
        }
    }

//    private fun navigateToPassword(loginDetails: LoginDetails?) {
//        if (loginDetails?.password != null && loginDetails.phone != null) {
//            if (loginDetails.password.isEmpty()) {
//                coroutineScope.launch(Dispatchers.IO) {
//                    viewModel.sendOtp(loginDetails.phone)
//                }
//            } else {
//                findNavController().navigate(
//                    LoginFragmentDirections.actionVerifyPassword(
//                        userDetails
//                    )
//                )
//            }
//        } else {
//            showAlert(
//                context = requireContext(),
//                message = getString(R.string.error_null_response)
//            )
//        }
//    }
//
//    private fun navigateToOTP() {
//        findNavController().navigate(LoginFragmentDirections.actionVerifyOtp(userDetails))
//    }
}
