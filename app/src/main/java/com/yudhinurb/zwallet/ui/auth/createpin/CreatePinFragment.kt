package com.yudhinurb.zwallet.ui.auth.createpin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentCreatePinBinding


class CreatePinFragment : Fragment() {
    private lateinit var binding: FragmentCreatePinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatePinBinding.inflate(layoutInflater)
        return binding.root
    }
}