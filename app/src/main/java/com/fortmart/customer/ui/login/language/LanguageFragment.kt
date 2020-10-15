package com.fortmart.customer.ui.login.language

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fortmart.customer.R
import com.fortmart.customer.databinding.FragmentLanguageBinding
import com.fortmart.customer.shared.Constants.LANGUAGE
import com.fortmart.customer.shared.Constants.USER_DETAILS
import kotlinx.android.synthetic.main.fragment_language.*

class LanguageFragment : Fragment() {

    private lateinit var binding: FragmentLanguageBinding
    private val submitListener = View.OnClickListener {
        val language = when (languageRadioGroup.checkedRadioButtonId) {
            R.id.english -> 0
            else -> 1
        }
        requireContext()
            .getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE)
            .edit()
            .putInt(LANGUAGE, language)
            .apply()
        findNavController().navigate(LanguageFragmentDirections.actionLogin())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)
        binding.submitClickListener = submitListener
        return binding.root
    }
}
