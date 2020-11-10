package com.fortmart.customer.ui.login.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentLoginBinding
import com.fortmart.customer.di.Injectable
import com.fortmart.customer.di.ViewModelFactory
import com.fortmart.customer.shared.Constants
import com.fortmart.customer.utils.*
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LoginFragment : Fragment(), Injectable {

    private val parentJob = Job()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var storedVerificationId: String
    private lateinit var verifiedPhoneNumber: String
    private lateinit var mFirebaseAuth: FirebaseAuth
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var otpResendCountDown: CountDownTimer

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            otp1.setText("${credential.smsCode?.get(0)}")
            otp2.setText("${credential.smsCode?.get(1)}")
            otp3.setText("${credential.smsCode?.get(2)}")
            otp4.setText("${credential.smsCode?.get(3)}")
            otp5.setText("${credential.smsCode?.get(4)}")
            otp6.setText("${credential.smsCode?.get(5)}")
            signInWithPhoneAuthCredential(credential)
        }
        override fun onVerificationFailed(e: FirebaseException) {
            otpScreen.hide()
            phoneNumberScreen.show()
            val message = when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    "Invalid phone number."
                }
                is FirebaseTooManyRequestsException -> {
                    "The SMS quota for the project has been exceeded."
                }
                else -> {
                    "Phone number verification failed."
                }
            }
            showAlert(requireContext(), message)
        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            loginProgressBar.hide()
            storedVerificationId = verificationId
            resendToken = token
        }
        override fun onCodeAutoRetrievalTimeOut(p0: String) {
            super.onCodeAutoRetrievalTimeOut(p0)
            loginProgressBar.hide()
        }
    }

    private val phoneSubmitListener = View.OnClickListener {
        loginProgressBar.show()
        val username = editTextUsername.text.toString()
        if (!username.isBlank()) {
            when {
                username.matches(Constants.phoneNumberPattern) -> {
                    loginProgressBar.hide()
                    verifiedPhoneNumber = getString(R.string.country_code) + username
                    phoneNumberScreen.hide()
                    otpScreen.show()
                    otp1.requestFocus()
                    title.text =
                        "Enter the OTP sent to ${getString(R.string.country_code)} $username"
                    startResendOTPCountdown()
//                    initiateVerification()
                }
                else -> {
                    binding.editTextUsername.error = getString(R.string.error_invalid_username)
                    loginProgressBar.hide()
                }
            }
        } else {
            binding.editTextUsername.error = getString(R.string.error_username_space)
            loginProgressBar.hide()
        }
    }

    private val otpSubmitListener = View.OnClickListener {
        loginProgressBar.show()
        val otpCode = otp1.text.toString() +
                otp2.text.toString() +
                otp3.text.toString() +
                otp4.text.toString() +
                otp5.text.toString() +
                otp6.text.toString()
        if (otpCode.isBlank() || otpCode.length == 6) {
            loginToDashboard()
//            signInWithPhoneAuthCredential(
//                PhoneAuthProvider.getCredential(
//                    storedVerificationId,
//                    otpCode
//                )
//            )

        } else {
            binding.editTextUsername.error = getString(R.string.error_empty_otp)
            loginProgressBar.hide()
        }
    }

    private val resendClickListener = View.OnClickListener {
        loginProgressBar.show()
        startResendOTPCountdown()
        initiateVerification()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.phoneSubmitClickListener = phoneSubmitListener
        binding.otpSubmitClickListener = otpSubmitListener
        binding.resendClicklistener = resendClickListener
        binding.editTextUsername.addTextChangedListener {
            binding.editTextUsername.error = null
        }
        val termsAndPolicyText = "By login you are agree to the terms & conditions and privacy policy."
        val spanText = SpannableString(termsAndPolicyText)
        val termsClickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(requireContext(), "terms & conditions", Toast.LENGTH_SHORT).show()
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        val termsLinkText = "terms & conditions"
        val termsStartIndex = termsAndPolicyText.indexOf(termsLinkText)
        val termsEndIndex = termsAndPolicyText.indexOf(termsLinkText) + termsLinkText.length
        spanText.setSpan(termsClickableSpan, termsStartIndex, termsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val privacyClickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(requireContext(), "privacy policy", Toast.LENGTH_SHORT).show()
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        val privacyLinkText = "privacy policy"
        val privacyStartIndex = termsAndPolicyText.indexOf(privacyLinkText)
        val privacyEndIndex = termsAndPolicyText.indexOf(privacyLinkText) + privacyLinkText.length
        spanText.setSpan(privacyClickableSpan, privacyStartIndex, privacyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.termsAndPolicyRadio.text = spanText
        binding.termsAndPolicyRadio.movementMethod = LinkMovementMethod.getInstance()
        binding.editTextUsername.setOnEditorActionListener(FieldSubmitListener { binding.ButtonSubmit.callOnClick() })
        mFirebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addOtpKeyListeners()
        addObservers()
    }

    private fun addObservers() {
        viewModel.apply {
            getRetrofitStatus().observe(viewLifecycleOwner, { setLoader(it) })
        }
    }

    private fun addOtpKeyListeners() {
        otp1.setOnEditorActionListener(FieldSubmitListener { ButtonSubmit.callOnClick() })
        otp2.setOnEditorActionListener(FieldSubmitListener { ButtonSubmit.callOnClick() })
        otp3.setOnEditorActionListener(FieldSubmitListener { ButtonSubmit.callOnClick() })
        otp4.setOnEditorActionListener(FieldSubmitListener { ButtonSubmit.callOnClick() })
        otp5.setOnEditorActionListener(FieldSubmitListener { ButtonSubmit.callOnClick() })
        otp6.setOnEditorActionListener(FieldSubmitListener { ButtonSubmit.callOnClick() })
        otp1.addTextChangedListener(OtpTextChangeListeners(otp1))
        otp2.addTextChangedListener(OtpTextChangeListeners(otp2))
        otp3.addTextChangedListener(OtpTextChangeListeners(otp3))
        otp4.addTextChangedListener(OtpTextChangeListeners(otp4))
        otp5.addTextChangedListener(OtpTextChangeListeners(otp5))
        otp6.addTextChangedListener(OtpTextChangeListeners(otp6))
        otp1.setOnKeyListener(otpKeyListener)
        otp2.setOnKeyListener(otpKeyListener)
        otp3.setOnKeyListener(otpKeyListener)
        otp4.setOnKeyListener(otpKeyListener)
        otp5.setOnKeyListener(otpKeyListener)
        otp6.setOnKeyListener(otpKeyListener)
    }

    private fun initiateVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            verifiedPhoneNumber,
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            callbacks,
            resendToken
        )
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mFirebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user.toString()
                    //TODO get auth token from api using 'user' param & in success call 'loginToDashboard()'
                    loginToDashboard()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        showAlert(requireContext(), "Invalid OTP.")
                    } else {
                        showAlert(requireContext(), "Failed to login.")
                    }
                }
            }
    }

    private fun startResendOTPCountdown() {
        resendCode.text = getString(R.string.label_resend_otp_disabled)
        resendCode.textSize = 14f
        resendCode.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorTextDark
            )
        )
        if (!::otpResendCountDown.isInitialized) {
            otpResendCountDown = object : CountDownTimer(59000, 1000) {
                override fun onFinish() {
                    if (this@LoginFragment.isVisible && !this@LoginFragment.isRemoving) {
                        countDown.text = ""
                        resendCode.text = getString(R.string.label_resend_otp_enabled)
                        resendCode.textSize = 18f
                        resendCode.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.colorAccent
                            )
                        )
                    }
                }

                override fun onTick(millisUntilFinished: Long) {
                    countDown.text = timerConverter(millisUntilFinished)
                }
            }
        }
        otpResendCountDown.cancel()
        otpResendCountDown.start()
    }

    private fun loginToDashboard() {
        requireContext().getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(Constants.LOGGED_IN, true).apply()
        loginProgressBar.hide()
        otpResendCountDown.cancel()
        activity?.startActivity(Intent.makeRestartActivityTask(activity?.intent?.component))
    }
}
