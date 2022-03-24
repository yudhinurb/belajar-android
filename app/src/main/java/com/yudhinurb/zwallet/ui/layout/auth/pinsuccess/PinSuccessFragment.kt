package com.yudhinurb.zwallet.ui.layout.auth.pinsuccess

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentCreatePinBinding
import com.yudhinurb.zwallet.databinding.FragmentPinSuccessBinding
import com.yudhinurb.zwallet.ui.layout.main.MainActivity
import com.yudhinurb.zwallet.ui.widget.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinSuccessFragment : Fragment() {
    private lateinit var binding: FragmentPinSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPinSuccessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnLogin.setOnClickListener {
            Handler().postDelayed({
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()}, 2000)
        }
    }
}