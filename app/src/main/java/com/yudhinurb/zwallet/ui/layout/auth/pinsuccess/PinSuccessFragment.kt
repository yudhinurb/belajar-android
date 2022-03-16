package com.yudhinurb.zwallet.ui.layout.auth.pinsuccess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yudhinurb.zwallet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinSuccessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin_success, container, false)
    }
}